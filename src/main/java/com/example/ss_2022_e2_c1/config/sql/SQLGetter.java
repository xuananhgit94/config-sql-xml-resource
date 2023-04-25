package com.example.ss_2022_e2_c1.config.sql;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.util.stream.IntStream;

public class SQLGetter {
    private NodeList nodeList;

    public SQLGetter(String filepath) {
        try {
            var factory = DocumentBuilderFactory.newInstance();
            var builder = factory.newDocumentBuilder();
            Document document;
            try (var inputStream = new FileInputStream(filepath)) {
                document = builder.parse(inputStream);
            }
            nodeList = document.getElementsByTagName("sql");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String idSql) {
        return IntStream.range(0, nodeList.getLength())
                .mapToObj(nodeList::item)
                .filter(node -> idSql.equals(node.getAttributes().getNamedItem("id").getNodeValue()))
                .flatMap(node -> {
                    var nodeListChild = node.getChildNodes();
                    return IntStream.range(0, nodeListChild.getLength())
                            .mapToObj(nodeListChild::item)
                            .filter(nodeChild -> "postgres".equals(nodeChild.getNodeName()))
                            .findFirst()
                            .map(Node::getTextContent).stream();
                })
                .findFirst()
                .orElse("");
    }
}
