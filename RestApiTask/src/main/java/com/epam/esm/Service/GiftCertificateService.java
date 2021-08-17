package com.epam.esm.Service;

import com.epam.esm.Configuration.DbConfig;
import com.epam.esm.Entity.GiftCertificate;
import com.epam.esm.Entity.Tag;
import com.epam.esm.Payload.ApiResponse;
import com.epam.esm.Payload.GiftCertificateDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GiftCertificateService {

    public ApiResponse getAllGiftCertificates() throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate";
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<GiftCertificate> certificates = new ArrayList<>();

        if (!resultSet.next()) {
            return new ApiResponse("There is no any Gift Certificate", false);
        } else {
            do {
                ResultSet resultSet1 = statement.executeQuery(" select t.id, t.name from gift_certificate_tags c join tag t on c.tags_id = t.id where gift_certificate_id= " + resultSet.getInt("id"));
                List<Tag> tags = new ArrayList<>();
                while (resultSet1.next()) {
                    tags.add(new Tag(resultSet1.getInt(1), resultSet1.getString(2)));
                }
                certificates.add(new GiftCertificate(resultSet.getInt(1),
                        resultSet.getString("name"), resultSet.getString("description"),
                        resultSet.getInt("duration"), resultSet.getInt("price"),
                        resultSet.getString("create_date"), resultSet.getString("last_update_date"), tags));
            } while (resultSet.next());
        }

        return new ApiResponse("Success", true, certificates);
    }

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

        GiftCertificate giftCertificate = new GiftCertificate(resultSet.getInt(1),
                resultSet.getString("name"), resultSet.getString("description"),
                resultSet.getInt("duration"), resultSet.getInt("price"),
                resultSet.getString("create_date"), resultSet.getString("last_update_date"), tags);

        return new ApiResponse("success", true, giftCertificate);
    }

    public ApiResponse addGiftCertificate(GiftCertificateDTO giftCertificateDTO) throws SQLException, ClassNotFoundException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String create_date = timestamp.toString();

        String sql = "insert into gift_certificate(name,description,duration,price,create_date,last_update_date)" +
                "values('" + giftCertificateDTO.getName() + "','" + giftCertificateDTO.getDescription() + "','"
                + giftCertificateDTO.getDuration() + "','" + giftCertificateDTO.getPrice() + "','" + create_date + "','" + create_date + "');";
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        statement.execute(sql);

        for (Integer tag : giftCertificateDTO.getTags()) {
            String sqlTag = "insert into gift_certificate_tags(gift_certificate_id, tags_id)" + "values(" +
                    getAddedCertificateId(giftCertificateDTO.getName()) + "," + tag + ")";
            statement.execute(sqlTag);
        }

        return new ApiResponse("Gift certificate added successfully", true);
    }

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


