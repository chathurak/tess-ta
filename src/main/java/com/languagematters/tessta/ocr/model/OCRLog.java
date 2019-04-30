package com.languagematters.tessta.ocr.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class OCRLog implements Serializable {

    private final static long serialVersionUID = -450899766674907767L;
    @SerializedName("originalFileName")
    @Expose
    private String originalFileName;
    @SerializedName("dirAbsolutePath")
    @Expose
    private String dirAbsolutePath;

    /**
     * No args constructor for use in serialization
     */
    public OCRLog() {
    }

    /**
     * @param dirAbsolutePath
     * @param originalFileName
     */
    public OCRLog(String originalFileName, String dirAbsolutePath) {
        super();
        this.originalFileName = originalFileName;
        this.dirAbsolutePath = dirAbsolutePath;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getDirAbsolutePath() {
        return dirAbsolutePath;
    }

    public void setDirAbsolutePath(String dirAbsolutePath) {
        this.dirAbsolutePath = dirAbsolutePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("originalFileName", originalFileName).append("dirAbsolutePath", dirAbsolutePath).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(dirAbsolutePath).append(originalFileName).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof OCRLog) == false) {
            return false;
        }
        OCRLog rhs = ((OCRLog) other);
        return new EqualsBuilder().append(dirAbsolutePath, rhs.dirAbsolutePath).append(originalFileName, rhs.originalFileName).isEquals();
    }

}