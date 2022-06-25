package com.space.dao.coppieLingue;

import com.space.dao.CommonDao;
import com.space.dto.ListResult;
import com.space.dto.ObjectResult;
import com.space.dto.SingleResult;
import com.space.mapper.MapperLoggerProxy;

import com.space.mapper.coppieLingue.CoppieLingueMapper;
import com.space.mapper.table.AreeStudioMapper;
import com.space.model.coppieLingue.CoppiaLingue;
import com.space.model.coppieLingue.Lingua;
import com.space.model.table.AreeStudio;
import com.space.model.table.T04Nazioni;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class CoppieLingueDao extends CommonDao {

    private Logger log = LoggerFactory.getLogger( getClass() );
    private CoppieLingueMapper mapper;

    public CoppieLingueDao(SqlSession session) {
        super(session);
        mapper = MapperLoggerProxy.wrap( session.getMapper( CoppieLingueMapper.class ), CoppieLingueMapper.class );
    }

    public List<CoppiaLingue> search(CoppiaLingue cl) {
        ListResult<CoppiaLingue> result = new ListResult<CoppiaLingue>();
        mapper.search(cl.getAnnoacc(),cl.getCscCod(),result);
        return result.getValue();
    }

    public void save(CoppiaLingue cl) {
        mapper.save(cl.getAnnoacc(),cl.getCscCod(),cl.getCodLingua1(),cl.getCodLingua2());
    }

    public void delete(CoppiaLingue cl) {
        mapper.delete(cl.getAnnoacc(),cl.getCscCod(), cl.getCodLingua1(), cl.getCodLingua2());
    }


    public int loadAnnoAcc(){
        return mapper.calcolaAnnoAccademico();
    }

    public List<Lingua> listLingue(){
        ListResult<Lingua> result = new ListResult<>();
        mapper.listaLingue(result);
        return result.getValue();
    }

}
