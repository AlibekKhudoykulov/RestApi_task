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

        if (certificates.isEmpty()) return new ApiResponse("There isn't any Gift Certificate", false);
        else return new ApiResponse("Success", true, certificates);

    }
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

        String sqlTag = "select t.id, t.name from gift_certificate_tags c join tag t on c.tags_id = t.id where gift_certificate_id=" + id;
        Statement statementTag = connection.createStatement();
        ResultSet resultSet1 = statementTag.executeQuery(sqlTag);

        if (!resultSet.next()) {
            return new ApiResponse("Gift certificate not found", false);
        }

        List<Tag> tags = new ArrayList<>();

        while (resultSet1.next()) {
            Tag tag = new Tag(resultSet1.getInt(1), resultSet1.getString(2));
            tags.add(tag);
        }

        GiftCertificate giftCertificate = new GiftCertificate(
                resultSet.getInt(1),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getInt("duration"),
                resultSet.getInt("price"),
                resultSet.getString("create_date"),
                resultSet.getString("last_update_date"),
                tags);

        return new ApiResponse("success", true, giftCertificate);
    }

    /**
     * Add Method (POST) for Gift Certificate
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


        for (Integer tag : tagList) {
            String sqlTag = "insert into gift_certificate_tags(gift_certificate_id, tags_id)"
                    + "values(" + addedCertificateId + "," + tag + ")";
            statement.execute(sqlTag);
        }
        return new ApiResponse("Gift Certificate is added successfully", true);
    }

    /**
     * Method for ADD Method (POST) of Gift Certificate Entity
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
     * @param id
     * @param giftCertificateDTO
     * @return ApiResponse with Message and success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse updateGiftCertificate(Integer id,GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        String sql="select * from gift_certificate where id="+id;
        Statement statement = DbConfig.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (!resultSet.next()){
            return new ApiResponse("Gift certificate not found",false);
        }

        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        String last_update = timestamp.toString();

        String updateSql= "update gift_certificate set name = '" + giftCertificateDTO.getName() + "', description = '" + giftCertificateDTO.getDescription()
                + "', price = " + giftCertificateDTO.getPrice() + ", duration = " + giftCertificateDTO.getDuration() + ", last_update_date = '" + last_update
                + "' where gift_certificate.id=" + id;

        statement.execute(updateSql);

        return new ApiResponse("Gift certificates Updated successfully",true);
    }

    /**
     * Delete Method of Gift Certificate Entity
     * @param id
     * @return ApiResponse with Message and Success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse deleteGiftCertificate(Integer id) throws SQLException, ClassNotFoundException {
        String sql="select * from gift_certificate where gift_certificate.id="+id;
        Statement statement = DbConfig.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (!resultSet.next()){
            return new ApiResponse("Gift certificate not found",false);
        }

        String deletingRelations="delete from gift_certificate_tags where gift_certificate_id="+id;
        statement.execute(deletingRelations);

        String deletingSql="delete from gift_certificate where gift_certificate.id="+id;
         statement.execute(deletingSql);

        return new ApiResponse("Row deleted successfully",true);
    }

}

