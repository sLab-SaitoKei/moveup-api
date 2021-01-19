package jp.co.vermore.form.admin;

import java.util.List;

public class DatatablesBaseForm{

    private int draw;

    private int start;

    private int length;

    private List<Column> columns;

    private List<Order> order;

    private Search search;

    private String orderStatement;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    public String getOrderStatement() {
        return orderStatement;
    }

    public void setOrderStatement(String orderStatement) {
        this.orderStatement = orderStatement;
    }

    public static class Column {
        private String data;
        private String name;
        private Boolean searchable;
        private Boolean orderable;
        private Search search;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getSearchable() {
            return searchable;
        }

        public void setSearchable(Boolean searchable) {
            this.searchable = searchable;
        }

        public Boolean getOrderable() {
            return orderable;
        }

        public void setOrderable(Boolean orderable) {
            this.orderable = orderable;
        }

        public Search getSearch() {
            return search;
        }

        public void setSearch(Search search) {
            this.search = search;
        }
    }

    public static class Order {
        private int column;
        private String dir;

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }
    }

    public static class Search {
        private String value;
        private Boolean regex;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Boolean isRegex() {
            return regex;
        }

        public void setRegex(Boolean regex) {
            this.regex = regex;
        }
    }
}
