package zihong.wu.cloudnote.common.mybatis;


/**
 * BaseEntity类提供了可供选择的多条件查询方式、排序方式、分页查询相关参数等
 * 当要使用复杂查询，如带条件的增删改查或分页查询时，需要继承此类
 * 数据实体类继承该类即可使用，例如：UserInfo extends BaseEntity
 */
public class BaseEntity {
    /**
     * 是否查询明细字段，false等价于*
     */
    private boolean baseKyleDetailed = true;
    /**
     * 多个查询条件是否用And连接
     */
    private Boolean baseKyleUseAnd = true;
    /**
     * 是否按排序关键字升序排列
     */
    private Boolean baseKyleUseASC = true;
    /**
     * 页面大小
     */
    private int baseKylePageSize = 10;
    /**
     * 要查询的页码
     */
    private int baseKyleCurrentPage = 1;
    /**
     * 根据页面大小和要查询的页码计算出的起始行号
     */
    private int baseKyleStartRows ;


    public boolean isBaseKyleDetailed() {
        return baseKyleDetailed;
    }

    public Boolean getBaseKyleUseAnd() {
        return baseKyleUseAnd;
    }

    public Boolean getBaseKyleUseASC() {
        return baseKyleUseASC;
    }

    public int getBaseKylePageSize() {
        return baseKylePageSize;
    }

    public int getBaseKyleCurrentPage() {
        return baseKyleCurrentPage;
    }

    public int getBaseKyleStartRows() {
        return baseKyleStartRows;
    }

    public void setBaseKyleDetailed(boolean baseKyleDetailed) {
        this.baseKyleDetailed = baseKyleDetailed;
    }

    public void setBaseKyleUseAnd(Boolean baseKyleUseAnd) {
        this.baseKyleUseAnd = baseKyleUseAnd;
    }

    public void setBaseKyleUseASC(Boolean baseKyleUseASC) {
        this.baseKyleUseASC = baseKyleUseASC;
    }


    public void setBaseKylePageSize(int baseKylePageSize) {
        this.baseKylePageSize = baseKylePageSize;
        //当baseKylePageSize调整时，baseKyleStartRows也需要调整
        this.baseKyleStartRows = this.baseKylePageSize * (this.baseKyleCurrentPage -1);
    }


    public void setBaseKyleCurrentPage(int baseKyleCurrentPage) {
        // baseKyleStartRows 是当前page的起始行，从零开始
        this.baseKyleStartRows = this.baseKylePageSize * (this.baseKyleCurrentPage - 1);
        this.baseKyleCurrentPage = baseKyleCurrentPage;

    }

    public void setBaseKyleStartRows(int baseKyleStartRows) {
        this.baseKyleStartRows = baseKyleStartRows;
    }
}
