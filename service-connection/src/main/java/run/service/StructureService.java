package run.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import run.bean.Node;
import run.mapper.StructureMapper;

import java.util.List;
import java.util.Map;

@Service
public class StructureService {
    @Autowired
    StructureMapper structureMapper;

    public List<Node> selectAll(){
        return structureMapper.selectAll();
    }

    public List<Map<String,Object>> selectStructureList(Map<String,Object> param){
        return structureMapper.selectStructureList(param);
    }

    public int insert(Node node){
        return structureMapper.insert(node);
    }

    public int delete(int id){
        return structureMapper.delete(id);
    }

    public int update(Map<String,Object> param){
        return structureMapper.update(param);
    }
}
