package com.epam.esm.Service;

import com.epam.esm.Configuration.DbConfig;
import com.epam.esm.Entity.GiftCertificate;
import com.epam.esm.Payload.ApiResponse;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateService {


    public ApiResponse getAllGiftCertificates() throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate";
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        List<GiftCertificate> giftCertificates = new ArrayList<>();
        if (!resultSet.next()) {
            return new ApiResponse("There isn't Gift Certificates", false);
        } else {
            do {
                GiftCertificate giftCertificate = new GiftCertificate(
                        resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getInt(4), resultSet.getInt(5), resultSet.getDate(6),
                        resultSet.getDate(7));
                giftCertificates.add(giftCertificate);
            } while (resultSet.next());
        }
        return new ApiResponse("success", true, giftCertificates);

    }

    public ApiResponse getGiftById(Integer id) throws SQLException, ClassNotFoundException {
        String sql = "select * from gift_certificate where id=" + id;
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        if (!resultSet.next()) {
            return new ApiResponse("Gift certificate not found", false);
        }
        GiftCertificate giftCertificate = new GiftCertificate(
                resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getInt(4), resultSet.getInt(5), resultSet.getDate(6),
                resultSet.getDate(7));

        return new ApiResponse("success", true, giftCertificate);
    }
}
