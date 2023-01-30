// package com.rumpus.common.ApiDB;

// import java.util.List;
// import java.util.Map;
// import java.util.function.Function;

// import java.sql.Connection;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.sql.Statement;

// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.support.GeneratedKeyHolder;

// import com.rumpus.common.Mapper;
// import com.rumpus.common.Model;
// import com.rumpus.common.ApiDB.Api.JDBC;

// public class ApiJdbc<T extends Model<T>> extends ApiDB<T, JDBC> implements IApiJdbc<T> {

//     private final static String API_NAME = "JdbcTemplate";
//     protected static JDBC jdbc;
//     protected Function<T, T> add;

//     public ApiJdbc(String table, Mapper<T> mapper) {
//         super(API_NAME, table, jdbc);
//         this.mapper = mapper;
//         // this.add = add;
//     }

//     // static {
//     //     jdbcTemplate = new JdbcTemplate();
//     // }

//     @Override
//     public boolean remove(int id) {
//         LOG.info("ApiJdbc::remove()");
//         // TODO: Check dependencies to delete
//         StringBuilder sb = new StringBuilder();
//         sb.append("DELETE FROM ")
//             .append(table)
//             .append(" WHERE ")
//             .append(id)
//             .append(" = ?;");
//         final String sql = sb.toString();
//         return JDBC.jdbcTemplate.update(sql, id) > 0;
//     }

//     @Override
//     public T get(int id) {
//         LOG.info("ApiJdbc::get()");
//         StringBuilder sb = new StringBuilder();
//         sb.append("SELECT * FROM ")
//             .append(table)
//             .append(" WHERE ")
//             .append(id)
//             .append(" = ?;");
//         final String sql = sb.toString();
//         LOG.info(sql);
//         return JDBC.jdbcTemplate.queryForObject(sql, mapper, id);
//     }

//     @Override
//     public List<T> getAll() {
//         LOG.info("ApiJdbc::getAll()");
//         StringBuilder sb = new StringBuilder();
//         sb.append("SELECT * FROM ").append(table).append(";");
//         final String sql = sb.toString();
//         LOG.info(sql);
//         List<T> objects = JDBC.jdbcTemplate.query(sql, mapper);
//         return objects;
//     }

//     @Override
//     public T add(T model) {
//         LOG.info("ApiJdbc::add()");

//         // Build sql
//         StringBuilder sbSql = new StringBuilder();
//         sbSql.append("INSERT INTO ").append(table).append("(");
//         for(Map.Entry<String, String> entry : model.getAttributes().entrySet()) {
//             sbSql.append(entry.getKey()).append(",");
//         }
//         sbSql.deleteCharAt(sbSql.length() - 1);
//         sbSql.append(") VALUES(");
//         for(Map.Entry<String, String> entry : model.getAttributes().entrySet()) {
//             sbSql.append("?,");
//         }
//         sbSql.deleteCharAt(sbSql.length() - 1);
//         sbSql.append(");");
//         final String sql = sbSql.toString();

//         GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
//         JDBC.jdbcTemplate.update((Connection conn) -> {
//             PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//             return model.getStatement().apply(statement);
//         }, keyHolder);
//         if(keyHolder.getKey() != null) {
//             model.setId(keyHolder.getKey().longValue());
//         } else {
//             model.setId(NO_ID);
//         }

//         return model;

//         // return add.apply(model);
//     }

//     public int setMapper(Mapper<T> mapper) {
//         this.mapper = mapper;
//         return SUCCESS;
//     }

//     public int setAddFunc(Function<T, T> add) {
//         this.add = add;
//         return SUCCESS;
//     }
// }
