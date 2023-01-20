package co.com.sofka.talentzone.retobackend.model;

public class PageDTO {
    public static final String FIRST_PAGE_NUM = "0";
    public static final int PAGESIZE = 10;
    private int pageNumber;

    public PageDTO(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
