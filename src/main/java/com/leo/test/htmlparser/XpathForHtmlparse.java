package com.leo.test.htmlparser;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class XpathForHtmlparse {

    public static void main(String[] args) throws Exception {
        // String content = FileUnitExt.toString(XpathForHtmlparse.class,
        // "html.xml", "utf-8");
        String content = "";
        Parser parser = Parser.createParser(content, "utf-8");
        HtmlPage page = new HtmlPage(parser);
        parser.visitAllNodesWith(page);

        NodeList nodes = page.getBody();

        NodeList Articles = getNodeListByXpath(nodes, "//div[@class='Article']");
        System.out.println("total Article:" + Articles.size());
        for (int i = 0; i < Articles.size(); i++) {
            NodeList ArticleChildren = Articles.elementAt(i).getChildren();
            Node a = getNodeByXpath(ArticleChildren, "div[@class='ArticleTitle']/span[@class='ArticleTitleText']/a");
            LinkTag al = (LinkTag) a;
            System.out.println("href:" + al.getLink() + " txt:" + al.getLinkText());
            System.out.println("href1:"
                               + getNodeTextByXpath(nodes,
                                                    "//div[@class='Article']/div[@class='ArticleTitle']/span[@class='ArticleTitleText']/a/@href"));
            System.out.println("href2:"
                               + getNodeTextByXpath(ArticleChildren,
                                                    "div[@class='ArticleTitle']/span[@class='ArticleTitleText']/a/@href"));
            // 指定第几个的时候，从1开始（如果要选第一个就写为1，对应索引0）
            System.out.println("href3:"
                               + getNodeTextByXpath(ArticleChildren, "div[1]/span[@class='ArticleTitleText']/a/@href"));
            System.out.println("href getLinkText:"
                               + getNodeTextByXpath(ArticleChildren,
                                                    "div[1]/span[@class='ArticleTitleText']/a/getLinkText()"));
        }
    }

    /**
     * 获得满足条件的节点的指定属性
     *
     * @param nodes
     * @param xpathstr
     * @return
     */
    public static String getNodeTextByXpath(NodeList nodes, String xpathstr) {
        if (isGetAttr(xpathstr)) {
            return (String) findByXpath(nodes, xpathstr);
        } else {
            throw new RuntimeException("xpath获取属性的时候，应该以“()”结尾或以\"@\"开头");
        }
    }

    /**
     * 是获得属性？
     *
     * @param xpathstr
     * @return
     */
    public static boolean isGetAttr(String xpathstr) {
        String lastNode = StringUtils.substringAfterLast(xpathstr, "/");
        boolean isGetNodeAttrStr = lastNode.endsWith("()") || lastNode.charAt(0) == '@';
        return isGetNodeAttrStr;
    }

    /**
     * 获得满足条件的list
     *
     * @param nodes
     * @param xpathstr
     * @return
     */
    public static NodeList getNodeListByXpath(NodeList nodes, String xpathstr) {
        if (!isGetAttr(xpathstr)) {
            return (NodeList) findByXpath(nodes, xpathstr);
        } else {
            throw new RuntimeException("xpath获取NodeList的时候，不应该以“()”结尾或以\"@\"开头");
        }
    }

    /**
     * 获得单个节点
     *
     * @param nodes
     * @param xpathstr
     * @return
     */
    public static Node getNodeByXpath(NodeList nodes, String xpathstr) {
        if (!isGetAttr(xpathstr)) {
            NodeList list = (NodeList) findByXpath(nodes, xpathstr);
            if (list != null) {
                if (list.size() == 1) {
                    return list.elementAt(0);
                } else {
                    throw new RuntimeException("xpath查询到" + list.size() + "个结果，请在xpath中指定");
                }
            } else {
                return null;
            }
        } else {
            throw new RuntimeException("xpath获取NodeList的时候，不应该以“()”结尾或以\"@\"开头");
        }
    }

    /**
     * 以“span[@class='ArticleTitleText']//a[1]/@href”为例，这个可以分为三段查找：<br>
     * “span[@class='ArticleTitleText']”、“//a”、“/@href”<br>
     * 每段查找的开始符号意义不同：<br>
     * 1.如果以“//”开头则是在现有nodelist以及其子中迭代查找；<br>
     * 2.如果以“/”开头则是在现有nodelist的子里面查找；<br>
     * 3.如果不以“/”开头则是在现有nodelist里面查找。<br>
     * 每段的末尾可以加一个条件限制，条件可以是索引(从1开始)，也可以是属性条件(如“[@class= 'ArticleTitleText']”表示查询条件为属性“class”等于“ArticleTitleText”)
     * 不支持其它的xpath语法了，差不多够用了，我现在使用 htmlcleaner ，所以这个不会再改进了
     *
     * @param nodes
     * @param xpathstr
     */
    public static Object findByXpath(NodeList nodes, String xpathstr) {
        String[] xpaths = xpathstr.split("/");
        String xpathtmp = null;
        String tagName = null;
        // 第一个迭代查找？
        boolean recursive = (xpathstr.indexOf("//") == 0);
        // 从当前层开始？
        boolean formNow = (xpathstr.charAt(0) != '/');
        // 要返回属性
        String lastNode = StringUtils.substringAfterLast(xpathstr, "/");
        boolean isGetNodeAttrStr = isGetAttr(xpathstr);
        if (isGetNodeAttrStr) {
            if (lastNode.endsWith("()")) {
                lastNode = lastNode.substring(0, lastNode.length() - 2);
            } else {
                lastNode = lastNode.substring(1);
            }
        }
        if (!recursive && !formNow) {
            NodeList newnodes = new NodeList();
            for (int k = 0; k < nodes.size(); k++) {
                NodeList chds = nodes.elementAt(k).getChildren();
                if (chds != null && chds.size() > 0) {
                    newnodes.add(chds);
                }
            }
            nodes = newnodes;
        }

        int beginIndex = (recursive ? 2 : (formNow ? 0 : 1));
        int endIndex = xpaths.length + (isGetNodeAttrStr ? -1 : 0);
        for (int i = beginIndex; i < endIndex; i++) {
            xpathtmp = StringUtils.trimToNull(xpaths[i]);
            // System.out.println(i + " " + xpathtmp);
            if (xpathtmp == null) {
                // 如果中间节点出现了“//”则是递归查找一次
                recursive = true;
                continue;
            }

            if (nodes == null || nodes.size() == 0) {
                System.out.println(i + " nodes is null.");
                return null;
            }

            NodeFilter myNodeFilter = null;
            // 有条件限制
            boolean hasTagAttr = (xpathtmp.indexOf("[") > 0);
            // 有条件限制的时候是属性查询条件还是索引
            boolean isAttr = false;
            // 索引的条件限制
            int nodexIdx = 0;
            if (hasTagAttr) {
                tagName = StringUtils.substringBefore(xpathtmp, "[");
                String tagAttr = StringUtils.substringBetween(xpathtmp, "[", "]");
                isAttr = (tagAttr.charAt(0) == '@');
                if (isAttr) {
                    final String attrName = StringUtils.substringBetween(tagAttr, "@", "=");
                    final String attrValue = StringUtils.substringBetween(tagAttr, "='", "'");
                    final String finalTagName = tagName;
                    myNodeFilter = new NodeFilter() {

                        /**
                         *
                         */
                        private static final long serialVersionUID = 8890810200803257429L;

                        public boolean accept(Node node) {
                            if ((node instanceof Tag) && !((Tag) node).isEndTag()
                                && ((Tag) node).getTagName().equals(finalTagName.toUpperCase())) {
                                if (attrValue.equals(((Tag) node).getAttribute(attrName))) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    };
                } else {
                    nodexIdx = Integer.parseInt(tagAttr) - 1;
                    myNodeFilter = new TagNameFilter(tagName);
                }
            } else {
                tagName = xpathtmp;
                myNodeFilter = new TagNameFilter(tagName);
            }

            NodeList newnodes = new NodeList();

            NodeList foundNodes = nodes.extractAllNodesThatMatch(myNodeFilter, recursive);
            if (foundNodes != null && foundNodes.size() > 0) {
                boolean isNotLast = (i < endIndex - 1);
                if (!hasTagAttr || (hasTagAttr && isAttr)) {
                    // 如果后面还有要查找的层次则取当前找到的各节点的子以备后面的查找，否则将当前找到的直接加入结果
                    if (isNotLast) {
                        for (int k = 0; k < foundNodes.size(); k++) {
                            NodeList chds = foundNodes.elementAt(k).getChildren();
                            if (chds != null && chds.size() > 0) {
                                newnodes.add(chds);
                            }
                        }
                    } else {
                        newnodes.add(foundNodes);
                    }
                } else if (hasTagAttr && !isAttr) {// 索引的，取单个
                    if (foundNodes.size() > nodexIdx + 1) {
                        // 如果后面还有要查找的层次则取当前找到的各节点的子以备后面的查找，否则将当前找到的直接加入结果
                        if (isNotLast) {
                            NodeList chds = foundNodes.elementAt(nodexIdx).getChildren();
                            if (chds != null && chds.size() > 0) {
                                newnodes.add(chds);
                            }
                        } else {
                            newnodes.add(foundNodes.elementAt(nodexIdx));
                        }
                    }
                }
            }

            nodes = newnodes;

            // 在所有子中查只应用到第一次
            if (recursive) {
                recursive = false;
            }
        }

        // 结果可以返回text或者nodelist
        if (isGetNodeAttrStr) {
            if (nodes != null && nodes.size() > 0) {
                String strtext = "";
                for (int i = 0; i < nodes.size(); i++) {
                    strtext = strtext + getNodeAttr(nodes.elementAt(i), lastNode) + "\r\n";
                }
                // System.out.println("lastAttr:" + lastAttr + ", Attr Text:"
                // + strtext);
                return strtext;
            } else {
                // System.out.println("no fit.");
                return null;
            }
        } else {
            return nodes;
        }
    }

    /**
     * 获得节点的属性
     *
     * @param node
     * @param attr
     * @return
     */
    public static String getNodeAttr(Node node, String attr) {
        if ("text".equals(attr)) {
            return node.getText();
        } else if ("getLink".equals(attr) || "getHref".equals(attr)) {
            if (node instanceof LinkTag) {
                return ((LinkTag) node).getLink();
            } else {
                throw new RuntimeException("节点不是 LinkTag 类型，无法获取 getLink()属性");
            }
        } else if ("getLinkText".equals(attr)) {
            if (node instanceof LinkTag) {
                return ((LinkTag) node).getLinkText();
            } else {
                throw new RuntimeException("节点不是 LinkTag 类型，无法获取 getLinkText()属性");
            }
        } else {
            try {
                return ((Tag) node).getAttribute(attr);
            } catch (Exception e) {
                return "";
            }
        }
    }
}
