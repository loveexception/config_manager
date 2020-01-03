package cn.tico.iot.configmanger.iot.excel;

import lombok.Data;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.impl.entity.NutEntity;
import org.nutz.log.Logs;

@Data
@Table("T_SON")
public class Son {

    @Column
    String name ;




    public static void main(String arg){
        Logs.get().debug("start");
        Son son = new Son();
        NutEntity t ;
        // son.body = null;
    }



}
