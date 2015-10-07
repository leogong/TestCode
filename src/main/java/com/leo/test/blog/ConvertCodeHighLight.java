package com.leo.test.blog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Created by leo on 10/5/15.
 */
public class ConvertCodeHighLight {

    static List<String> list = new ArrayList<String>();

    static {
        list.add("Bash");
        list.add("bash");
        list.add("c");
        list.add("clojure");
        list.add("cpp");
        list.add("csharp");
        list.add("css");
        list.add("diff");
        list.add("erlang");
        list.add("java");
        list.add("jscript");
        list.add("perl");
        list.add("php");
        list.add("plain");
        list.add("python");
        list.add("ruby");
        list.add("scala");
        list.add("shell");
        list.add("sql");
        list.add("xhtml");
        list.add("xml");
    }

    public static void main(String[] args) throws ParseException {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/b3log?useUnicode=yes&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("12345");
        jdbcTemplate.setDataSource(dataSource);

        List<WpPostDO> postDOs = jdbcTemplate.query("SELECT oId AS id,articleAbstract,articleContent FROM b3_solo_article  WHERE articleContent like '%<pre>%'",
                                                    new BeanPropertyRowMapper<>(WpPostDO.class));
        for (WpPostDO postDO : postDOs) {
            String articleAbstract = postDO.getArticleAbstract();
            String articleContent = postDO.getArticleContent();
            for (String s : list) {
                articleAbstract = articleAbstract.replaceAll("\\[" + s + "\\]",
                                                             "<pre class=\"prettyprint\">").replaceAll("\\[/" + s
                                                                                                       + "\\]",
                                                                                                       "</pre>");
                articleAbstract = articleAbstract.replaceAll("<pre>", "<pre class=\"prettyprint\">");
                articleContent = articleContent.replaceAll("\\[" + s + "\\]",
                                                           "<pre class=\"prettyprint\">").replaceAll("\\[/" + s + "\\]",
                                                                                                     "</pre>");
                articleContent = articleContent.replaceAll("<pre>", "<pre class=\"prettyprint\">");

            }
            jdbcTemplate.update("UPDATE b3_solo_article SET articleAbstract = ?,articleContent = ? WHERE oId = ?",
                                articleAbstract, articleContent, postDO.getId());

        }

    }
}
