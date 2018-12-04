package com.fallink.parent.generator.entity;


import com.fallink.parent.generator.money.Money;
import com.fallink.parent.generator.money.MoneyType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.type.YesNoType;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * DO类基础定义
 */
@MappedSuperclass
@TypeDefs({
        @TypeDef(defaultForType = Money.class, name = "moneyType", typeClass = MoneyType.class),
        @TypeDef(defaultForType = boolean.class, name = "yesNoType", typeClass = YesNoType.class),
        @TypeDef(defaultForType = Boolean.class, name = "yesNoType", typeClass = YesNoType.class),})
public abstract class BaseDO implements Serializable {

    private static final long serialVersionUID = 8498555510077615223L;

    public static final String COLUMN_NAME_RAW_ADD_TIME = "rawAddTime";

    public static final String COLUMN_NAME_RAW_UPDATE_TIME = "rawUpdateTime";

    @Column(name = "raw_add_time", insertable = false, updatable = false)
    protected Date rawAddTime;

    @Column(name = "raw_update_time", insertable = false, updatable = false)
    protected Date rawUpdateTime;

    public Date getRawAddTime() {
        return this.rawAddTime;
    }

    public void setRawAddTime(Date rawAddTime) {
        this.rawAddTime = rawAddTime;
    }

    public Date getRawUpdateTime() {
        return this.rawUpdateTime;
    }

    public void setRawUpdateTime(Date rawUpdateTime) {
        this.rawUpdateTime = rawUpdateTime;
    }

    public static String getColumnNameRawAddTime() {
        return COLUMN_NAME_RAW_ADD_TIME;
    }

    @Override
    public String toString() {
        return this.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj, new String[]{"rawAddTime",
                "rawUpdateTime"});
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, new String[]{"rawAddTime",
                "rawUpdateTime"});
    }
}
