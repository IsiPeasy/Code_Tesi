package com.space.mapper.corrCorsiPerAbb;

import com.space.dto.ListResult;
import com.space.model.CorsoDiStudio;
import com.space.model.coppieLingue.CoppiaLingue;
import com.space.model.coppieLingue.Lingua;
import com.space.model.corrispondenzecorsiperabbreviazioni.Corrispondenza;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface CorrCorsiPerAbbMapper {

    @Insert(
            "call SPACE_CORRISPONDENZA_CORSI.SEARCH_CORR("+
                    "#{annoacc,mode=IN,jdbcType=INTEGER},"+
                    "#{codicePratica,mode=IN,jdbcType=VARCHAR},"+
                    "#{result.value,mode=OUT,jdbcType=CURSOR, resultMap=corrispondenzeMap}" +
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE )
    public void search(
            @Param("annoacc") Integer annoacc,
            @Param("codicePratica") String codicePratica,
            @Param("result") ListResult<Corrispondenza> result);


    @Insert(
            "call SPACE_CORRISPONDENZA_CORSI.SAVE_CORR("+
                    "#{annoacc,mode=IN,jdbcType=INTEGER},"+
                    "#{codicePratica,mode=IN,jdbcType=VARCHAR},"+
                    "#{codiceImmatricolazione,mode=IN,jdbcType=VARCHAR}"+
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE)
    public void save(@Param("annoacc") Integer annoacc,
                     @Param("codicePratica") String codicePratica,
                     @Param("codiceImmatricolazione") String codiceImmatricolazione);


    @Insert(
            "call SPACE_CORRISPONDENZA_CORSI.DELETE_CORR("+
                    "#{annoacc,mode=IN,jdbcType=INTEGER},"+
                    "#{codicePratica,mode=IN,jdbcType=VARCHAR},"+
                    "#{codiceImmatricolazione,mode=IN,jdbcType=VARCHAR}"+
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE)
    public void delete(@Param("annoacc") Integer annoacc,
                       @Param("codicePratica") String codicePratica,
                       @Param("codiceImmatricolazione") String codiceImmatricolazione);



    @Select(
            "select calcola_annoacc(sysdate) from dual"
    )
    public int calcolaAnnoAccademico();


    @Insert(
            "call SPACE_CORRISPONDENZA_CORSI.LISTA_CORR_CORSI("+
                    "#{result.value,mode=OUT,jdbcType=CURSOR, resultMap=corsoDiStudioMap}" +
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE )
    public void listaCorrCorsi(
            @Param("result") ListResult<CorsoDiStudio> result);


}
