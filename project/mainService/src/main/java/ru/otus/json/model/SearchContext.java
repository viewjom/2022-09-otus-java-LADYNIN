package ru.otus.json.model;

public class SearchContext {
    private String contentType;
    private String query;
    private String withParent;
    private String limit;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getWithParent() {
        return withParent;
    }

    public void setWithParent(String withParent) {
        this.withParent = withParent;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
