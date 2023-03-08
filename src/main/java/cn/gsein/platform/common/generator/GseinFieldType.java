package cn.gsein.platform.common.generator;

import java.util.Optional;

public enum GseinFieldType {
    VARCHAR("varchar", "String"),
    INT("int", "Integer"),
    BIGINT("bigint", "Long"),
    TINYINT("tinyint", "Integer"),
    DATETIME("datetime", "LocalDateTime"),
    DATE("date", "LocalDateTime"),
    TIMESTAMP("timestamp", "LocalDateTime"),
    TEXT("text", "String"),
    LONGTEXT("longtext", "String"),
    DECIMAL("decimal", "BigDecimal");

    private String dbType;
    private String javaType;

    GseinFieldType(String dbType, String javaType) {
        this.dbType = dbType;
        this.javaType = javaType;
    }

    public static Optional<GseinFieldType> of(String dbType) {
        for (GseinFieldType fieldType : GseinFieldType.values()) {
            if (fieldType.getDbType().equals(dbType)) {
                return Optional.of(fieldType);
            }
        }
        return Optional.empty();
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }
}
