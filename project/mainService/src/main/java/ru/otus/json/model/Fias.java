package ru.otus.json.model;

import java.util.List;

public class Fias {
    private SearchContext searchContext;
    private List<Result> result;

    public SearchContext getSearchContext() {
        return searchContext;
    }

    public void setSearchContext(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
