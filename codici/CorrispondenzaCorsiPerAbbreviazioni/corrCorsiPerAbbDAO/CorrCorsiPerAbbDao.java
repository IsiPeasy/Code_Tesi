package com.space.dao.corrCorsiPerAbb;

import com.space.dao.CommonDao;
import com.space.dto.ListResult;
import com.space.mapper.MapperLoggerProxy;
import com.space.mapper.corrCorsiPerAbb.CorrCorsiPerAbbMapper;
import com.space.model.CorsoDiStudio;
import com.space.model.corrispondenzecorsiperabbreviazioni.Corrispondenza;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CorrCorsiPerAbbDao extends CommonDao {

    private Logger log = LoggerFactory.getLogger( getClass() );
    private CorrCorsiPerAbbMapper mapper;

    public CorrCorsiPerAbbDao(SqlSession session) {
        super(session);
        mapper = MapperLoggerProxy.wrap( session.getMapper( CorrCorsiPerAbbMapper.class ), CorrCorsiPerAbbMapper.class );
    }

    public List<Corrispondenza> search(Corrispondenza c) {
        ListResult<Corrispondenza> result = new ListResult<Corrispondenza>();
        mapper.search(c.getAnnoField(),c.getCodicePratica(),result);
        return result.getValue();
    }

    public void save(Corrispondenza c) {
        mapper.save(c.getAnnoField(),c.getCodicePratica(),c.getCodiceImmatricolazione());
    }

    public void delete(Corrispondenza c) {
        mapper.delete(c.getAnnoField(),c.getCodicePratica(),c.getCodiceImmatricolazione());
    }

    public int loadAnnoAcc(){
        return mapper.calcolaAnnoAccademico();
    }

    public List<CorsoDiStudio> listaCorrCorsi(){
        ListResult<CorsoDiStudio> result = new ListResult<>();
        mapper.listaCorrCorsi(result);
        return result.getValue();
    }
}
