package cn.edu.nju.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Qiang
 * @since 27/02/2017
 */
@Entity
@Table(name = "member", schema = "hotel", catalog = "")
public class MemberEntity {
    private int id;
    private String name;
    private byte status;
    private int deposit;
    private int points;
    private String creditCard;
    private Timestamp registDate;
    private int remainDays;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "deposit", nullable = false)
    public int getDeposit() {
        return deposit;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    @Basic
    @Column(name = "points", nullable = false)
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Basic
    @Column(name = "credit_card", nullable = false, length = 6000)
    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    @Basic
    @Column(name = "regist_date", nullable = false)
    public Timestamp getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Timestamp registDate) {
        this.registDate = registDate;
    }

    @Basic
    @Column(name = "remain_days", nullable = false)
    public int getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberEntity that = (MemberEntity) o;

        if (id != that.id) return false;
        if (status != that.status) return false;
        if (deposit != that.deposit) return false;
        if (points != that.points) return false;
        if (remainDays != that.remainDays) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (creditCard != null ? !creditCard.equals(that.creditCard) : that.creditCard != null) return false;
        if (registDate != null ? !registDate.equals(that.registDate) : that.registDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (int) status;
        result = 31 * result + deposit;
        result = 31 * result + points;
        result = 31 * result + (creditCard != null ? creditCard.hashCode() : 0);
        result = 31 * result + (registDate != null ? registDate.hashCode() : 0);
        result = 31 * result + remainDays;
        return result;
    }
}
