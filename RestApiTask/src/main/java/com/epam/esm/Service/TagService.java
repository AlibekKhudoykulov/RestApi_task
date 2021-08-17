package com.epam.esm.Service;

import com.epam.esm.Configuration.DbConfig;
import com.epam.esm.Entity.GiftCertificate;
import com.epam.esm.Entity.Tag;
import com.epam.esm.Payload.ApiResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    /**
     *  Get ALL Tags of Tag Entity
     * @return ApiResponse with Message,Success(boolean) and Tags List
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse getAllTags() throws SQLException, ClassNotFoundException {
        String sql="select * from tag";
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        List<Tag> tags=new ArrayList<>();
        if (!resultSet.next()){
            return new ApiResponse("Tags not found",false);
        }else {
            do {
                tags.add(new Tag(resultSet.getInt("id"),resultSet.getString("name")));
            }while (resultSet.next());
            return new ApiResponse("Success",true,tags);
        }
    }

    /**
     *  Get Tag By ID Method of Tag Entity
     * @param id
     * @return ApiResponce with Message,Success(boolean) and One Tag
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse getTagById(Integer id) throws SQLException, ClassNotFoundException {
        String sql="select * from tag where id="+id;
        Connection connection = DbConfig.getConnection();
        ResultSet resultSet = connection.createStatement().executeQuery(sql);
        if (!resultSet.next()) return new ApiResponse("Tag not found",false);
        Tag tag=new Tag(resultSet.getInt("id"),resultSet.getString("name"));
        return new ApiResponse("Success",true,tag);
    }

    /**
     *  Delete Tag by ID of Tag Entity
     * @param id
     * @return ApiResponse with Message and Success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse deleteTagById(Integer id) throws SQLException, ClassNotFoundException {
        String sql="select * from tag where id="+id;
        Connection connection = DbConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next()) return new ApiResponse("Tag NOT FOUND",false);

        String deletingRelation="delete from gift_certificate_tags where tags_id="+id;
        String deleteSql="delete from tag where tag.id="+id;
        statement.execute(deletingRelation);
        statement.execute(deleteSql);

        return new ApiResponse("Row deleted successfully",true);

    }

    /**
     *  ADD Tag method of Tag Entity
     * @param tag
     * @return ApiResponse with Message and Success(boolean)
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public ApiResponse addTag(Tag tag) throws SQLException, ClassNotFoundException {
        String sql="insert into tag(name) values('"+tag.getName()+"')";
        Connection connection = DbConfig.getConnection();
        connection.createStatement().execute(sql);

        return new ApiResponse("Tag added SUCCESSFULLY",true);
    }
}
