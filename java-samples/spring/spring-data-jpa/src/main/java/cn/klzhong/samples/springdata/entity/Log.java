package cn.klzhong.samples.springdata.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tmp_log_test")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = false, precision = 20, scale = 0)
    private Long id;

    @Column(name = "query_id")
    private String queryId;

    @Column(name = "log")
    private String content;

    @Column(name = "insert_time", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
    private Date insertTime;

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getQueryId() {
        return queryId;
    }

    public void setQueryId(String queryId) {
        this.queryId = queryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
