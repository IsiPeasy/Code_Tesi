package com.space.mapper.coppieLingue;

import com.space.dto.ListResult;
import com.space.dto.SingleResult;
import com.space.model.coppieLingue.CoppiaLingue;
import com.space.model.coppieLingue.Lingua;
import com.space.model.corsisingoli.InsegnamentoScelto;
import com.space.model.table.T04Nazioni;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

public interface CoppieLingueMapper {

    @Insert(
            "call SPACE_COPPIE_LINGUE.SEARCH("+
                    "#{annoacc,mode=IN,jdbcType=INTEGER},"+
                    "#{codiceCorso,mode=IN,jdbcType=VARCHAR},"+
                    "#{result.value,mode=OUT,jdbcType=CURSOR, resultMap=coppieLingueMap}" +
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE )
    public void search(
            @Param("annoacc") Integer annoacc,
            @Param("codiceCorso") String codiceCorso,
            @Param("result") ListResult<CoppiaLingue> result);

-----------------------------------------------------------------------------------
    @Insert(
            "call SPACE_COPPIE_LINGUE.SAVE_COPPIA("+
                    "#{annoacc,mode=IN,jdbcType=INTEGER},"+
                    "#{codiceCorso,mode=IN,jdbcType=VARCHAR},"+
                    "#{linguaUno,mode=IN,jdbcType=VARCHAR},"+
                    "#{linguaDue,mode=IN,jdbcType=VARCHAR}" +
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE)
    public void save(@Param("annoacc") Integer annoacc,
                     @Param("codiceCorso") String codiceCorso,
                     @Param("linguaUno") String linguaUno,
                     @Param("linguaDue") String linguaDue);

--------------------------------------------------------------------------------------
    @Insert(
            "call SPACE_COPPIE_LINGUE.DELETE_COPPIA("+
                    "#{annoacc,mode=IN,jdbcType=INTEGER},"+
                    "#{codiceCorso,mode=IN,jdbcType=VARCHAR},"+
                    "#{linguaUno,mode=IN,jdbcType=VARCHAR},"+
                    "#{linguaDue,mode=IN,jdbcType=VARCHAR}" +
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE)
    public void delete(@Param("annoacc") Integer annoacc,
                       @Param("codiceCorso") String codiceCorso,
                       @Param("linguaUno") String linguaUno,
                       @Param("linguaDue") String linguaDue);

----------------------------------------------------------------------------------
    @Select(
            "select calcola_annoacc(sysdate) from dual"
    )
    public int calcolaAnnoAccademico ( );

----------------------------------------------------------------------------------
    @Insert(
            "call SPACE_COPPIE_LINGUE.LISTA_LINGUE("+
                    "#{result.value,mode=OUT,jdbcType=CURSOR, resultMap=linguaMap}" +
                    ")"
    )
    @Options(statementType = StatementType.CALLABLE )
    public void listaLingue(
            @Param("result") ListResult<Lingua> result);

}
