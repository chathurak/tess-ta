package com.languagematters.tessta.admin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class TrainedDataProperties implements Serializable {

    private final static long serialVersionUID = 5708204322516075546L;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     */
    public TrainedDataProperties() {
    }

    /**
     * @param timestamp
     * @param id
     * @param description
     * @param name
     */
    public TrainedDataProperties(String id, String timestamp, String name, String description) {
        super();
        this.id = id;
        this.timestamp = timestamp;
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("timestamp", timestamp).append("name", name).append("description", description).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(timestamp).append(id).append(description).append(name).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TrainedDataProperties) == false) {
            return false;
        }
        TrainedDataProperties rhs = ((TrainedDataProperties) other);
        return new EqualsBuilder().append(timestamp, rhs.timestamp).append(id, rhs.id).append(description, rhs.description).append(name, rhs.name).isEquals();
    }

}