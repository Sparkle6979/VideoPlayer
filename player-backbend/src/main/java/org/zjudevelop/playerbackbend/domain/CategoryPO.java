package org.zjudevelop.playerbackbend.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author sparkle6979l
 * @version 1.0
 * @data 2023/10/27 19:26
 */
@Data
@TableName(value = "category")
public class CategoryPO {

    private Long id;

    /**
     * 范围名称
     */
    @TableField("category_name")
    private String categoryName;

    @TableField("create_time")
    private Timestamp createTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryPO category = (CategoryPO) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
