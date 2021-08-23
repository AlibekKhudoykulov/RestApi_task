package com.epam.esm.service;


import com.epam.esm.configuration.DbConfig;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.payload.ApiResponse;
import com.epam.esm.payload.GiftCertificateDTO;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateService {

    /**
     * GET ALL Method for Gift Certificate entity
     *
     * @return List of Gift Certificate
     * @throws SQLException
     * @throws ClassNotFoundException
     */

    public ApiResponse getAllGiftCertificates() throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate";
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<GiftCertificate> certificates = new ArrayList<>();

        if (DbConfig.isMyResultEmpty(resultSet)){
            return new ApiResponse("Gifts NOT FOUND",false);
        }

        while (resultSet.next()) {
            certificates.add(new GiftCertificate(
                    resultSet.getInt(1),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("duration"),
                    resultSet.getString("create_date"),
                    resultSet.getString("last_update_date"),
                    getGiftCertificateTags(resultSet.getInt("id"))));
        }

        return new ApiResponse("Success", true, certificates);

    }

    /**
     *  Method for get gift certificates tags
     * @param id
     * @return tags
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    private List<Tag> getGiftCertificateTags(int id) throws SQLException, ClassNotFoundException {
        String sql = "select t.id, t.name from gift_certificate_tags c join tag t on c.tags_id = t.id where gift_certificate_id= " + id;
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Tag> tags = new ArrayList<>();
        while (resultSet.next()) {
            tags.add(new Tag(resultSet.getInt(1), resultSet.getString(2)));
        }
        return tags;
    }

    /**
     * Get Gift By Id Method for Gift Certificate Entity
     * @param id
     * @return Gift Certificate
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse getGiftById(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate where id=" + id;
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);


        if (DbConfig.isMyResultEmpty(resultSet)){
            return new ApiResponse("Gift NOT FOUND",false);
        }
        resultSet.next();
            GiftCertificate giftCertificate = new GiftCertificate(
                    resultSet.getInt(1),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getInt("duration"),
                    resultSet.getInt("price"),
                    resultSet.getString("create_date"),
                    resultSet.getString("last_update_date"),
                    getGiftCertificateTags(resultSet.getInt("id")));

        return new ApiResponse("success", true, giftCertificate);
    }
    /**
     * Get Gifts by Tag name
     * @param name
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse getGiftsByTagName(String name) throws SQLException, ClassNotFoundException {

        String sql="select g.* from gift_certificate_tags c" +
                "    join" +
                "    gift_certificate g on c.gift_certificate_id = g.id" +
                "    join" +
                "    tag t on c.tags_id = t.id where t.name='"+name+"'";

        ResultSet resultSet = DbConfig.getConnection().createStatement().executeQuery(sql);

        if (DbConfig.isMyResultEmpty(resultSet)){
            return new ApiResponse("Gifts NOT FOUND",false);
        }

        GiftCertificate giftCertificate = new GiftCertificate(
                resultSet.getInt(1),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("duration"),
                resultSet.getInt("price"),
                resultSet.getString("create_date"),
                resultSet.getString("last_update_date"),
                getGiftCertificateTags(resultSet.getInt("id")));

        return new ApiResponse("Gift by tag name", true, giftCertificate);
    }

    /**
     *  Method for searching with part of Gift name
     * @param giftName
     * @return Gift
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse getGiftByPartOfName(String giftName) throws SQLException, ClassNotFoundException {
        String sql="select * from gift_certificate where name ilike '%"+giftName+"%'";
        ResultSet resultSet = DbConfig.getConnection().createStatement().executeQuery(sql);

        List<GiftCertificate> giftCertificates=new ArrayList<>();

        if (DbConfig.isMyResultEmpty(resultSet)){
            return new ApiResponse("Gifts NOT FOUND",false);
        }

        while (resultSet.next()) {
            giftCertificates.add(new GiftCertificate(
                    resultSet.getInt(1),
                    resultSet.getString("name"),
                    resultSet.getString("description"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("duration"),
                    resultSet.getTimestamp("create_date").toString(),
                    resultSet.getTimestamp("last_update_date").toString(),
                    getGiftCertificateTags(resultSet.getInt("id"))));
        }
        return new ApiResponse("Gift certificates FOUND",true,giftCertificates);

    }

    /**
     * Method for Get gifts with sorted created date
     * @return List of sorted gifts
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse getSortedGiftsByCreatedDate() throws SQLException, ClassNotFoundException {
        String sql="select * from gift_certificate order by create_date asc";
        ResultSet rs = DbConfig.getConnection().createStatement().executeQuery(sql);
        List<GiftCertificate> certificates = new ArrayList<>();

        if (DbConfig.isMyResultEmpty(rs)){
            return new ApiResponse("Gifts NOT FOUND",false);
        }

        while (rs.next()) {
            certificates.add(new GiftCertificate(
                    rs.getInt(1),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("duration"),
                    rs.getString("create_date"),
                    rs.getString("last_update_date"),
                    getGiftCertificateTags(rs.getInt("id"))));
        }

        return new ApiResponse("Success", true, certificates);
    }

    /**
     * Add Method (POST) for Gift Certificate
     *
     * @param giftCertificateDTO
     * @return ApiResponse with Message and Success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse addGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String created_date = timestamp.toString();
        String sql = "insert into gift_certificate(name,description,duration,price,create_date,last_update_date)" +
                "values" + "('"
                + giftCertificateDTO.getName() + "','"
                + giftCertificateDTO.getDescription() + "',"
                + giftCertificateDTO.getDuration() + ","
                + giftCertificateDTO.getPrice() + ",'"
                + created_date + "','"
                + created_date + "');";

        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);

        List<Integer> tagList = giftCertificateDTO.getTags();
        Integer addedCertificateId = getAddedCertificateId(giftCertificateDTO.getName());


        if (tagList != null && tagList.size() == 0) {
            for (Integer tag : tagList) {
                String sqlTag = "insert into gift_certificate_tags(gift_certificate_id, tags_id)"
                        + "values(" + addedCertificateId + "," + tag + ")";
                statement.execute(sqlTag);
            }
        }
        return new ApiResponse("Gift Certificate is added successfully", true);
    }

    /**
     * Method for ADD Method (POST) of Gift Certificate Entity
     *
     * @param name
     * @return Integer Id of Added Gift Certificate
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public Integer getAddedCertificateId(String name) throws SQLException, ClassNotFoundException {
        String sql = "select gift_certificate.id from gift_certificate where gift_certificate.name=" + "'" + name + "'";
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            Integer anInt = resultSet.getInt(1);
            return anInt;
        }

        return null;
    }


    /**
     * UPDATE (PUT) Method for Gift Certificate Entity
     *
     * @param id
     * @param giftCertificateDTO
     * @return ApiResponse with Message and success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse updateGiftCertificate(Integer id, GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate where id=" + id;
        Statement statement = DbConfig.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (DbConfig.isMyResultEmpty(resultSet)){
            return new ApiResponse("Gift NOT FOUND",false);
        }

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String last_update = timestamp.toString();

        String updateSql = "update gift_certificate set name = '" + giftCertificateDTO.getName() + "', description = '" + giftCertificateDTO.getDescription()
                + "', price = " + giftCertificateDTO.getPrice() + ", duration = " + giftCertificateDTO.getDuration() + ", last_update_date = '" + last_update
                + "' where gift_certificate.id=" + id;

        statement.execute(updateSql);

        return new ApiResponse("Gift certificates Updated successfully", true);
    }

    /**
     * Delete Method of Gift Certificate Entity
     *
     * @param id
     * @return ApiResponse with Message and Success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse deleteGiftCertificate(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate where gift_certificate.id=" + id;
        Statement statement = DbConfig.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (DbConfig.isMyResultEmpty(resultSet)){
            return new ApiResponse("Gift NOT FOUND",false);
        }

        String deletingRelations = "delete from gift_certificate_tags where gift_certificate_id=" + id;
        statement.execute(deletingRelations);

        String deletingSql = "delete from gift_certificate where gift_certificate.id=" + id;
        statement.execute(deletingSql);

        return new ApiResponse("Row deleted successfully", true);
    }


}


