package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.mapper.TotalMapper;

import java.util.List;
import java.util.Map;

@Service
public class TotalService {
    @Autowired
    TotalMapper totalMapper;

    public List<Map<String,Object>> selectRTUOff(Map<String,Object> param){
        return totalMapper.selectRTUOff(param);
    }
    public List<Map<String,Object>> selectRTUWarning(Map<String,Object> param){
        return totalMapper.selectRTUWarning(param);
    }

    public List<Map<String,Object>> selectDeviceWarning(Map<String,Object> param){
        return totalMapper.selectDeviceWarning(param);
    }

    public List<Map<String,Object>> selectDeviceOff(Map<String,Object> param){
        return totalMapper.selectDeviceOff(param);
    }

    public List<Map<String,Object>> selectSiteById(Map<String,Object> param){
        return totalMapper.selectSiteById(param);
    }

    public Map<String,Object> selectRTUById(int rtu_id){
        return totalMapper.selectRTUById(rtu_id);
    }

    public int selectRTUNumBySiteId(Map<String,Object> param){
        return totalMapper.selectRTUNumBySiteId(param);
    }

    public List<Map<String,Object>> selectSPDCount(Map<String,Object> param){
        return totalMapper.selectSPDCount(param);
    }

    public List<Map<String,Object>> selectSPDPort(Map<String,Object> param){
        return totalMapper.selectSPDPort(param);
    }

    public List<Map<String,Object>> selectETCRCount(Map<String,Object> param){
        return totalMapper.selectETCRCount(param);
    }

    public List<Map<String,Object>> selectETCRPort(Map<String,Object> param){
        return totalMapper.selectETCRPort(param);
    }

    public List<Map<String,Object>> selectLightningCount(Map<String,Object> param){
        return totalMapper.selectLightningCount(param);
    }

    public List<Map<String,Object>> selectLightningPort(Map<String,Object> param){
        return totalMapper.selectLightningPort(param);
    }

    public List<Map<String,Object>> selectStaticCount(Map<String,Object> param){
        return totalMapper.selectStaticCount(param);
    }

    public List<Map<String,Object>> selectStaticPort(Map<String,Object> param){
        return totalMapper.selectStaticPort(param);
    }

    public List<Map<String,Object>> selectRswsCount(Map<String,Object> param){
        return totalMapper.selectRswsCount(param);
    }

    public List<Map<String,Object>> selectRswsPort(Map<String,Object> param){
        return totalMapper.selectRswsPort(param);
    }

    public List<Map<String,Object>> selectSvtCount(Map<String,Object> param){
        return totalMapper.selectSvtCount(param);
    }

    public List<Map<String,Object>> selectSvtPort(Map<String,Object> param){
        return totalMapper.selectSvtPort(param);
    }

    public List<Map<String,Object>> selectHcCount(Map<String,Object> param){
        return totalMapper.selectHcCount(param);
    }

    public List<Map<String,Object>> selectHcPort(Map<String,Object> param){
        return totalMapper.selectHcPort(param);
    }

    public List<Map<String,Object>> selectStrayCount(Map<String,Object> param){
        return totalMapper.selectStrayCount(param);
    }

    public List<Map<String,Object>> selectStray485Port(Map<String,Object> param){
        return totalMapper.selectStray485Port(param);
    }

    public List<Map<String,Object>> selectStrayTestPort(Map<String,Object> param){
        return totalMapper.selectStrayTestPort(param);
    }

    public List<Map<String,Object>> selectCatCount(Map<String,Object> param){
        return totalMapper.selectCatCount(param);
    }

    public List<Map<String,Object>> selectCat485Port(Map<String,Object> param){
        return totalMapper.selectCat485Port(param);
    }

    public List<Map<String,Object>> selectCatTestPort(Map<String,Object> param){
        return totalMapper.selectCatTestPort(param);
    }

    public List<Map<String,Object>> selectDeviceOffByMonth(Map<String,Object> param){
        return totalMapper.selectDeviceOffByMonth(param);
    }

    public List<Map<String,Object>> selectDeviceWarningByMonth(Map<String,Object> param){
        return totalMapper.selectDeviceWarningByMonth(param);
    }

    public List<Map<String,Object>> selectRTUOffByMonth(Map<String,Object> param){
        return totalMapper.selectRTUOffByMonth(param);
    }

    public List<Map<String,Object>> selectSPDHistory(Map<String,Object> param){
        return totalMapper.selectSPDHistory(param);
    }

    public List<Map<String,Object>> selectETCRHistory(Map<String,Object> param){
        return totalMapper.selectETCRHistory(param);
    }

    public List<Map<String,Object>> selectLightningHistory(Map<String,Object> param){
        return totalMapper.selectLightningHistory(param);
    }

    public List<Map<String,Object>> selectStaticHistory(Map<String,Object> param){
        return totalMapper.selectStaticHistory(param);
    }

    public List<Map<String,Object>> selectRswsHistory(Map<String,Object> param){
        return totalMapper.selectRswsHistory(param);
    }

    public List<Map<String,Object>> selectSvtHistory(Map<String,Object> param){
        return totalMapper.selectSvtHistory(param);
    }

    public List<Map<String,Object>> selectHcHistory(Map<String,Object> param){
        return totalMapper.selectHcHistory(param);
    }

    public List<Map<String,Object>> selectStrayHistory(Map<String,Object> param){
        return totalMapper.selectStrayHistory(param);
    }

    public List<Map<String,Object>> selectCatHistory(Map<String,Object> param){
        return totalMapper.selectCatHistory(param);
    }

    public int selectSPDCountByTime(Map<String,Object> param){
        return totalMapper.selectSPDCountByTime(param);
    }

    public int selectETCRCountByTime(Map<String,Object> param){
        return totalMapper.selectETCRCountByTime(param);
    }

    public int selectLightningCountByTime(Map<String,Object> param){
        return totalMapper.selectLightningCountByTime(param);
    }

    public int selectStaticCountByTime(Map<String,Object> param){
        return totalMapper.selectStaticCountByTime(param);
    }

    public int selectRswsCountByTime(Map<String,Object> param){
        return totalMapper.selectRswsCountByTime(param);
    }

    public int selectSvtCountByTime(Map<String,Object> param){
        return totalMapper.selectSvtCountByTime(param);
    }

    public int selectHcCountByTime(Map<String,Object> param){
        return totalMapper.selectHcCountByTime(param);
    }

    public int selectStrayCountByTime(Map<String,Object> param){
        return totalMapper.selectStrayCountByTime(param);
    }

    public int selectCatCountByTime(Map<String,Object> param){
        return totalMapper.selectCatCountByTime(param);
    }

    public int selectSPDTotal(Map<String,Object> param){
        return totalMapper.selectSPDTotal(param);
    }

    public int selectETCRTotal(Map<String,Object> param){
        return totalMapper.selectETCRTotal(param);
    }

    public int selectLightningTotal(Map<String,Object> param){
        return totalMapper.selectLightningTotal(param);
    }

    public int selectStaticTotal(Map<String,Object> param){
        return totalMapper.selectStaticTotal(param);
    }

    public int selectRswsTotal(Map<String,Object> param){
        return totalMapper.selectRswsTotal(param);
    }

    public int selectSvtTotal(Map<String,Object> param){
        return totalMapper.selectSvtTotal(param);
    }

    public int selectHcTotal(Map<String,Object> param){
        return totalMapper.selectHcTotal(param);
    }

    public int selectStrayTotal(Map<String,Object> param){
        return totalMapper.selectStrayTotal(param);
    }

    public int selectCatTotal(Map<String,Object> param){
        return totalMapper.selectCatTotal(param);
    }

    public int selectSiteTotal(Map<String,Object> param){
        return totalMapper.selectSiteTotal(param);
    }

    public int selectRTUTotal(Map<String,Object> param){
        return totalMapper.selectRTUTotal(param);
    }

    public List<Map<String,Object>> selectSiteWarningTotal(Map<String,Object> param){
        return totalMapper.selectSiteWarningTotal(param);
    }

    public List<Map<String,Object>> selectSiteDeviceOffTotal(Map<String,Object> param){
        return totalMapper.selectSiteDeviceOffTotal(param);
    }

    public List<Map<String,Object>> selectSiteOffTotal(Map<String,Object> param){
        return totalMapper.selectSiteOffTotal(param);
    }

    public List<Integer> selectRTUStatusBySiteId(Map<String,Object> param){
        return totalMapper.selectRTUStatusBySiteId(param);
    }
}
