package com.chuonye.syso.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * Markdown 转换辅助工具
 * 
 * @author chuonye@foxmail.com
 */
public class MarkdownUtil {

    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(Parser.EXTENSIONS, Arrays.asList(
                    YamlFrontMatterExtension.create())
                )
            .set(HtmlRenderer.RENDER_HEADER_ID, true)
            .set(HtmlRenderer.SOFT_BREAK, "<br />\n")
            .toImmutable();
    
    final private static Parser PARSER = Parser.builder(OPTIONS).build();
    final private static HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();
    
    public static String renderToHtml(String markdown) {
        Node document = PARSER.parse(markdown);
        return RENDERER.render(document);
    }
    
    public static Map<String, List<String>> getYamlFrontMatter(String markdown) {
        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(markdown);
        visitor.visit(document);
        return visitor.getData();
    }
}
