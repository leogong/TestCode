package com.leo.test.blog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.CollectionUtils;

/**
 * leo Created by leo on 8/28/15.
 */
public class Import {

    // <!--more-->
    // <!--nextpage-->
    // (?<=\[)([a-zA-Z]+?)(?=\].*?\[\/\1\])
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM");
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/b3log?useUnicode=yes&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("12345");
        jdbcTemplate.setDataSource(dataSource);

        List<WpPostDO> wpPostDOs = jdbcTemplate.query("SELECT id,post_date AS postDate,post_content AS postContent,post_title AS postTitle FROM wp_posts WHERE post_status = ? #AND ID = 1784",
                                                      new BeanPropertyRowMapper<>(WpPostDO.class), "publish");
        for (WpPostDO wpPostDO : wpPostDOs) {
            Long id = wpPostDO.getId();
            Date postDate = wpPostDO.getPostDate();
            String postContent = wpPostDO.getPostContent();
            // todo replace high light code

            String postTitle = wpPostDO.getPostTitle();
            String abstractArticle = postContent;
            String permalink = "/" + id + ".html";
            Set<String> tags = new HashSet<>();

            List<Long> relationIds = jdbcTemplate.queryForList("SELECT term_taxonomy_id FROM wp_term_relationships WHERE object_id = ?",
                                                               Long.class, id);
            if (!CollectionUtils.isEmpty(relationIds)) {
                for (Long relationId : relationIds) {
                    Long termId = jdbcTemplate.queryForList("SELECT term_id FROM wp_term_taxonomy WHERE term_taxonomy_id = ?",
                                                            Long.class, relationId).get(0);
                    String tagName = jdbcTemplate.queryForList("SELECT name FROM wp_terms WHERE term_id = ?",
                                                               String.class, termId).get(0);
                    tags.add(String.valueOf(tagName));
                }
            }

            String more = "<!--more-->";
            int i = postContent.indexOf(more);
            if (i > 0) {
                abstractArticle = postContent.substring(0, i);
                postContent = postContent.replaceAll(more, "");
                String page = "<!--nextpage-->";
                postContent = postContent.replaceAll(page, "");
            }
            String insertSql = "INSERT INTO b3_solo_article VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(insertSql, id, postTitle, abstractArticle, StringUtils.join(tags, ","),
                                "email", 0, 0, postContent, permalink, 1, 1, 0, postDate, postDate,
                                Math.random(), 1, 1, "", "tinyMCE");

            for (String tag : tags) {
                tag = tag.replaceAll("/", "|").replaceAll("\\\\", "|");
                List<Long> existTags = jdbcTemplate.queryForList("SELECT oId FROM b3_solo_tag WHERE tagTitle = ?",
                                                                 Long.class, tag);
                Long tagId = null;
                if (!CollectionUtils.isEmpty(existTags)) {
                    tagId = existTags.get(0);
                    jdbcTemplate.update("UPDATE b3_solo_tag SET tagPublishedRefCount = tagPublishedRefCount+1,tagReferenceCount = tagReferenceCount+1 WHERE oId = ?",
                                        tagId);
                }
                if (tagId == null) {
                    tagId = genTimeMillisId();
                    jdbcTemplate.update("INSERT INTO b3_solo_tag VALUES (?,?,?,?)", tagId, 1, 1, tag);
                }

                jdbcTemplate.update("INSERT INTO b3_solo_tag_article VALUES (?,?,?)", genTimeMillisId(), id, tagId);

            }

            jdbcTemplate.update("UPDATE b3_solo_statistic SET statisticBlogArticleCount = statisticBlogArticleCount+1,statisticPublishedBlogArticleCount=statisticPublishedBlogArticleCount+1 WHERE oId=?",
                                "statistic");

            long time = sf.parse(sf.format(postDate)).getTime();
            List<Long> archiveIds = jdbcTemplate.queryForList("SELECT oId FROM b3_solo_archiveDate WHERE archiveTime = ?",
                                                              Long.class, time);
            Long archiveId = null;
            if (!CollectionUtils.isEmpty(archiveIds)) {
                archiveId = archiveIds.get(0);
                jdbcTemplate.update("UPDATE b3_solo_archiveDate SET archiveDateArticleCount=archiveDateArticleCount+1,archiveDatePublishedArticleCount=archiveDatePublishedArticleCount+1 WHERE oId = ?",
                                    archiveId);
            }
            if (archiveId == null) {
                archiveId = genTimeMillisId();
                jdbcTemplate.update("INSERT INTO b3_solo_archiveDate VALUES (?,?,?,?)", archiveId, 1, 1, time);
            }

            jdbcTemplate.update("INSERT INTO b3_solo_archiveDate_article VALUES (?,?,?)", genTimeMillisId(), archiveId,
                                id);

            jdbcTemplate.update("UPDATE b3_solo_user SET userArticleCount = userArticleCount+1,userPublishedArticleCount=userPublishedArticleCount+1 WHERE userEmail = ?",
                                "email");

        }
    }

    public static synchronized Long genTimeMillisId() {
        Long ret = null;

        try {
            ret = System.currentTimeMillis();

            try {
                Thread.sleep(50);
            } catch (final InterruptedException e) {
                throw new RuntimeException("Generates time millis id fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
