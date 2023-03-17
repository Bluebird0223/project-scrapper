// public class product {
    
// }
// package com.scrapper;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {

    public static void main(String[] args) {
        String url = "https://www.officedepot.com/a/browse/ergonomic-office-chairs/N=5+593065&cbxRefine=1429832/";

        try {

            Document doc;

            try {
                // fetching the target website
                doc = Jsoup
                        .connect(url)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36")
                        .get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Document document = Jsoup.connect(url).get();

            // Extract product details

            Elements products = doc.select("div.od-product-card-region-body");
            FileWriter productCSV = new FileWriter("chairs.csv", true);
            productCSV.write("Item Number,Product Name,Product Price,ProductImage\n");

            for (Element product : products.subList(0, 10)) {
                String productName = product.selectFirst("a.od-product-card-description").text().split(",")[0];
                String productPrice = product.selectFirst("span.od-graphql-price-big-price").text();
                String itemNumber = product.selectFirst("div.od-product-card-region-product-number").text();
                String productImage = product.selectFirst("img.od-sku-image").attr("src");
                // Store product details in a CSV file
                productCSV.write(itemNumber + "," + productName + "," + productPrice + "," + productImage + "\n");
            }
            System.out.println("Product details exported to product.csv");
            productCSV.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
