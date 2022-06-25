package com.space.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.space.dto.ObjectResult;
import com.space.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.*;

public interface CommonMapper{


	public ArrayList<HashMap<String,Object>> executeSelect(@Param("sql") String sql);
	
	@Insert(" call dbms_session.set_identifier(#{clientId}) ")
	@Options( statementType = StatementType.CALLABLE )
	public void setClientId( @Param("clientId")String clientID);
	
	@Insert(
		"call calcola_anno_accademico(" +
			"#{date,mode=IN}," +
			"#{result.value,mode=OUT,jdbcType=INTEGER}" +
		")"
	)
	@Options( statementType = StatementType.CALLABLE )
	public void loadAnnoAccademico( @Param("date")Date date, @Param("result")ObjectResult<Integer> result );

    @Insert(
            "call SPACE_CARSS_LOG.INSERT_LOG(" +
                    "#{idServizio,mode=IN}," +
                    "#{username,mode=IN}," +
                    "#{transaction,mode=IN}," +
                    "#{thread,mode=IN}," +
                    "#{sessionid,mode=IN}," +
                    "#{operation,mode=IN}," +
                    "#{detail,mode=IN}" +
                    ")"
    )
    @Options( statementType = StatementType.CALLABLE )
    public void insertOperationLog(@Param("idServizio")String idServizio,
                                   @Param("username")String username,
                                   @Param("transaction")String transaction,
                                   @Param("thread")String thread,
                                   @Param("sessionid")String sessionid,
                                   @Param("operation")String operation,
                                   @Param("detail")String detail);
	
	public int executeUpdate( @Param("sql")String sql );




}
