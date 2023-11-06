package com.example.myhike;

public class StarRatingGenerator {
    public static String generateStarRating(int maxRating, int rating) {
        StringBuilder html = new StringBuilder();

        for (int i = 0; i < maxRating; i++) {
            if (i < rating) {
                html.append("<div class=\"star-rating\">");
                html.append("<i class=\"fas fa-star d-inline-block\"></i>");
                html.append("</div>");
            } else {
                html.append("<div class=\"inactive\">");
                html.append("<i class=\"fas fa-star d-inline-block\"></i>");
                html.append("</div>");
            }
        }

        return html.toString();
    }
}
