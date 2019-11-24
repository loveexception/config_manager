package cn.tico.iot.configmanger.module.iot.models.Topo;

import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;

@Data
public class Point implements Comparable<Point>{
    public int row;
    public int col;

    public Point(){
        
    }
    public Point(int row,int col){
        this.row = row;
        this.col = col;
    }


    @Override
    public int compareTo(Point o) {
        return CompareToBuilder.reflectionCompare(this,o);
    }
}
