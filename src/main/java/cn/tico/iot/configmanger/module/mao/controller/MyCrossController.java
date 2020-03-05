package cn.tico.iot.configmanger.module.mao.controller;

import cn.tico.iot.configmanger.common.adaptor.GraphQLAdaptor;
import cn.tico.iot.configmanger.module.mao.common.BaseController;
import cn.tico.iot.configmanger.module.mao.graphql.*;
import com.google.common.collect.Lists;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import org.nutz.ioc.aop.Aop;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.segment.CharSegment;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.*;
import org.nutz.mvc.impl.ActionInvoker;

import java.util.List;
import java.util.Map;

import static org.nutz.integration.jedis.RedisInterceptor.jedis;


/**
 *
 *
 * @author maodajun
 * @date 2020-02-16
 */
//@At("/")
@IocBean
public class MyCrossController extends BaseController{


    static  final String URL100 =    "/iot/kind/add/*" ;
    static  final String URL101 =    "/api//all_sno"             ;
    static  final String URL102 =    "/api//device"              ;
    static  final String URL103 =    "/api//devices"             ;
    static  final String URL104 =    "/api//dict"                ;
    static  final String URL105 =    "/api//gateway"             ;
    static  final String URL106 =    "/api//gatewayGraphql"      ;
    static  final String URL107 =    "/api//graphql"             ;
    static  final String URL108 =    "/api//init_snos"           ;
    static  final String URL109 =    "/api//kafka"               ;
    static  final String URL110 =    "/api//topu"                ;
    static  final String URL111 =    "/cms/category"             ;
    static  final String URL112 =    "/cms/category/add/?";
    static  final String URL113 =    "/cms/category/add" ;
    static  final String URL114 =    "/cms/category/adddo"       ;
    static  final String URL115 =    "/cms/category/edit/?"      ;
    static  final String URL116 =    "/cms/category/editdo"      ;
    static  final String URL117 =    "/cms/category/list"        ;
    static  final String URL118 =    "/cms/category/remove/?"    ;
    static  final String URL119 =    "/cms/category/selectTree/?" ;
    static  final String URL120 =    "/cms/category/treedata"    ;
    static  final String URL121 =    "/demo/echo"                ;
    static  final String URL122 =    "/demo/ping"                ;
    static  final String URL123 =    "/graph//device"            ;
    static  final String URL124 =    "/graph//sql"               ;
    static  final String URL125 =    "/graph//sql"               ;
    static  final String URL126 =    "/iot/device"               ;
    static  final String URL127 =    "/iot/device/device_check"  ;
    static  final String URL128 =    "/iot/device/device_insert_all" ;
    static  final String URL129 =    "/iot/device/device_insert_update" ;
    static  final String URL130 =    "/iot/device/device_list"   ;
    static  final String URL131 =    "/iot/device/device_one"    ;
    static  final String URL132 =    "/iot/device/device_remove" ;
    static  final String URL133 =    "/iot/device/device_update_all" ;
    static  final String URL134 =    "/iot/device/device_upload" ;
    static  final String URL135 =    "/iot/device/drivers_tags"  ;
    static  final String URL136 =    "/iot/device/drivers_update" ;
    static  final String URL137 =    "/iot/device/over"          ;
    static  final String URL138 =    "/iot/device/person_add_all" ;
    static  final String URL139 =    "/iot/device/person_add_update" ;
    static  final String URL140 =    "/iot/device/person_add_update" ;
    static  final String URL141 =    "/iot/device/person_grade_add" ;
    static  final String URL142 =    "/iot/device/person_grade_add_all" ;
    static  final String URL143 =    "/iot/device/person_grade_remove" ;
    static  final String URL144 =    "/iot/device/person_grades" ;
    static  final String URL145 =    "/iot/device/person_list"   ;
    static  final String URL146 =    "/iot/device/person_ruler_change" ;
    static  final String URL147 =    "/iot/driver"               ;
    static  final String URL148 =    "/iot/driver/dorp_types"    ;
    static  final String URL149 =    "/iot/driver/driver_insert_all" ;
    static  final String URL150 =    "/iot/driver/driver_insert_update" ;
    static  final String URL151 =    "/iot/driver/driver_kind"   ;
    static  final String URL152 =    "/iot/driver/driver_list"   ;
    static  final String URL153 =    "/iot/driver/driver_one"    ;
    static  final String URL154 =    "/iot/driver/driver_page"   ;
    static  final String URL155 =    "/iot/driver/driver_remove" ;
    static  final String URL156 =    "/iot/driver/driver_temple" ;
    static  final String URL157 =    "/iot/driver/driver_update_all" ;
    static  final String URL158 =    "/iot/driver/driver_upload" ;
    static  final String URL159 =    "/iot/driver/drivers"       ;
    static  final String URL160 =    "/iot/driver/grade_add"     ;
    static  final String URL161 =    "/iot/driver/grade_all_save" ;
    static  final String URL162 =    "/iot/driver/grade_list"    ;
    static  final String URL163 =    "/iot/driver/grade_remove"  ;
    static  final String URL164 =    "/iot/driver/normal_change" ;
    static  final String URL165 =    "/iot/driver/normal_csv_path" ;
    static  final String URL166 =    "/iot/driver/normal_insert_all" ;
    static  final String URL167 =    "/iot/driver/normal_list"   ;
    static  final String URL168 =    "/iot/driver/normal_names"  ;
    static  final String URL169 =    "/iot/driver/normal_remove" ;
    static  final String URL170 =    "/iot/driver/normal_update_all" ;
    static  final String URL171 =    "/iot/driver/ruler_add"     ;
    static  final String URL172 =    "/iot/driver/ruler_change"  ;
    static  final String URL173 =    "/iot/driver/ruler_remove"  ;
    static  final String URL174 =    "/iot/gateway"              ;
    static  final String URL175 =    "/iot/gateway/add"          ;
    static  final String URL176 =    "/iot/gateway/adddo"        ;
    static  final String URL177 =    "/iot/gateway/edit/?"       ;
    static  final String URL178 =    "/iot/gateway/editdo"       ;
    static  final String URL179 =    "/iot/gateway/gateway_list" ;
    static  final String URL180 =    "/iot/gateway/list"         ;
    static  final String URL181 =    "/iot/gateway/remove"       ;
    static  final String URL182 =    "/iot/gateway/selectExtSno" ;
    static  final String URL183 =    "/iot/gateway/selectTree/?" ;
    static  final String URL184 =    "/iot/gateway/subgateway"   ;
    static  final String URL185 =    "/iot/kind"                 ;
    static  final String URL186 =    "/iot/kind/add";
    static  final String URL187 =    "/iot/kind/adddo"           ;
    static  final String URL188 =    "/iot/kind/edit/?"          ;
    static  final String URL189 =    "/iot/kind/editdo"          ;
    static  final String URL190 =    "/iot/kind/list"            ;
    static  final String URL191 =    "/iot/kind/remove/?"        ;
    static  final String URL192 =    "/iot/kind/select_parent"   ;
    static  final String URL193 =    "/iot/kind/select_parent_self" ;
    static  final String URL194 =    "/iot/kind/selectTree/?"    ;
    static  final String URL195 =    "/iot/kind/treedata"        ;
    static  final String URL196 =    "/iot/kind/treedataorg"     ;
    static  final String URL197 =    "/iot/kind/treeobject"      ;
    static  final String URL198 =    "/iot/location"             ;
    static  final String URL199 =    "/iot/location/add";
    static  final String URL200 =    "/iot/location/adddo"       ;
    static  final String URL201 =    "/iot/location/edit/?"      ;
    static  final String URL202 =    "/iot/location/editdo"      ;
    static  final String URL203 =    "/iot/location/list"        ;
    static  final String URL204 =    "/iot/location/remove/?"    ;
    static  final String URL205 =    "/iot/location/select_parent" ;
    static  final String URL206 =    "/iot/location/select_parent_self" ;
    static  final String URL207 =    "/iot/location/selectTree/?" ;
    static  final String URL208 =    "/iot/location/tree_parent" ;
    static  final String URL209 =    "/iot/location/treedata"    ;
    static  final String URL210 =    "/iot/location/treeobject"  ;
    static  final String URL211 =    "/iot/person/find_add_one"  ;
    static  final String URL212 =    "/iot/person/grade_add"     ;
    static  final String URL213 =    "/iot/person/grade_all_save" ;
    static  final String URL214 =    "/iot/person/grade_list"    ;
    static  final String URL215 =    "/iot/person/grade_remove"  ;
    static  final String URL216 =    "/iot/person/normal_list"   ;
    static  final String URL217 =    "/iot/person/person"        ;
    static  final String URL218 =    "/iot/person/person_id"     ;
    static  final String URL219 =    "/iot/person/person_remove" ;
    static  final String URL220 =    "/iot/person/person_update" ;
    static  final String URL221 =    "/iot/person/ruler_add"     ;
    static  final String URL222 =    "/iot/person/ruler_remove"  ;
    static  final String URL223 =    "/iot/tag"                  ;
    static  final String URL224 =    "/iot/tag/add"              ;
    static  final String URL225 =    "/iot/tag/adddo"            ;
    static  final String URL226 =    "/iot/tag/edit/?"           ;
    static  final String URL227 =    "/iot/tag/editdo"           ;
    static  final String URL228 =    "/iot/tag/list"             ;
    static  final String URL229 =    "/iot/tag/remove"           ;
    static  final String URL230 =    "/iot/tag/tag_page"         ;
    static  final String URL231 =    "/iot/tag/tags"             ;
    static  final String URL232 =    "/login"                    ;
    static  final String URL233 =    "/login/login"              ;
    static  final String URL234 =    "/login/logout"             ;
    static  final String URL235 =    "/login/unauth"             ;
    static  final String URL236 =    "/mao/pars"                 ;
    static  final String URL237 =    "/mao/pars/add"             ;
    static  final String URL238 =    "/mao/pars/adddo"           ;
    static  final String URL239 =    "/mao/pars/edit/?"          ;
    static  final String URL240 =    "/mao/pars/editdo"          ;
    static  final String URL241 =    "/mao/pars/list"            ;
    static  final String URL242 =    "/mao/pars/remove"          ;
    static  final String URL243 =    "/mao/pushs"                ;
    static  final String URL244 =    "/mao/pushs/add"            ;
    static  final String URL245 =    "/mao/pushs/add_list_do"    ;
    static  final String URL246 =    "/mao/pushs/adddo"          ;
    static  final String URL247 =    "/mao/pushs/edit/?"         ;
    static  final String URL248 =    "/mao/pushs/editdo"         ;
    static  final String URL249 =    "/mao/pushs/list"           ;
    static  final String URL250 =    "/mao/pushs/remove"         ;
    static  final String URL251 =    "/mao/upgrades"             ;
    static  final String URL252 =    "/mao/upgrades/add"         ;
    static  final String URL253 =    "/mao/upgrades/adddo"       ;
    static  final String URL254 =    "/mao/upgrades/edit/?"      ;
    static  final String URL255 =    "/mao/upgrades/editdo"      ;
    static  final String URL256 =    "/mao/upgrades/list"        ;
    static  final String URL257 =    "/mao/upgrades/remove"      ;
    static  final String URL258 =    "/monitor/logininfor"       ;
    static  final String URL259 =    "/monitor/logininfor/clean" ;
    static  final String URL260 =    "/monitor/logininfor/list"  ;
    static  final String URL261 =    "/monitor/logininfor/remove" ;
    static  final String URL262 =    "/monitor/online"           ;
    static  final String URL263 =    "/monitor/online/add"       ;
    static  final String URL264 =    "/monitor/online/adddo"     ;
    static  final String URL265 =    "/monitor/online/edit/?"    ;
    static  final String URL266 =    "/monitor/online/editdo"    ;
    static  final String URL267 =    "/monitor/online/list"      ;
    static  final String URL268 =    "/monitor/online/remove"    ;
    static  final String URL269 =    "/monitor/operLog"          ;
    static  final String URL270 =    "/monitor/operLog/add"      ;
    static  final String URL271 =    "/monitor/operLog/clean"    ;
    static  final String URL272 =    "/monitor/operLog/detail/?" ;
    static  final String URL273 =    "/monitor/operLog/list"     ;
    static  final String URL274 =    "/monitor/operLog/remove"   ;
    static  final String URL275 =    "/monitor/server"           ;
    static  final String URL276 =    "/open/file/get/?"          ;
    static  final String URL277 =    "/open/file/upload"         ;
    static  final String URL278 =    "/open/weixin/get"          ;
    static  final String URL279 =    "/open/weixin/post"         ;
    static  final String URL280 =    "/permission"               ;
    static  final String URL281 =    "/sys/area"                 ;
    static  final String URL282 =    "/sys/area/add" ;
    static  final String URL283 =    "/sys/area/adddo"           ;
    static  final String URL284 =    "/sys/area/edit/?"          ;
    static  final String URL285 =    "/sys/area/editdo"          ;
    static  final String URL286 =    "/sys/area/initdata"        ;
    static  final String URL287 =    "/sys/area/list"            ;
    static  final String URL288 =    "/sys/area/remove/?"        ;
    static  final String URL289 =    "/sys/area/selectTree/?"    ;
    static  final String URL290 =    "/sys/area/treedata"        ;
    static  final String URL291 =    "/sys/config"               ;
    static  final String URL292 =    "/sys/config/add"           ;
    static  final String URL293 =    "/sys/config/adddo"         ;
    static  final String URL294 =    "/sys/config/edit/?"        ;
    static  final String URL295 =    "/sys/config/editdo"        ;
    static  final String URL296 =    "/sys/config/export"        ;
    static  final String URL297 =    "/sys/config/list"          ;
    static  final String URL298 =    "/sys/config/remove"        ;
    static  final String URL299 =    "/sys/dept"                 ;
    static  final String URL300 =    "/sys/dept/add/?"           ;
    static  final String URL301 =    "/sys/dept/adddo"           ;
    static  final String URL302 =    "/sys/dept/checkdeptnameunique" ;
    static  final String URL303 =    "/sys/dept/edit/?"          ;
    static  final String URL304 =    "/sys/dept/editdo"          ;
    static  final String URL305 =    "/sys/dept/list"            ;
    static  final String URL306 =    "/sys/dept/remove/?"        ;
    static  final String URL307 =    "/sys/dept/selectTree/?"    ;
    static  final String URL308 =    "/sys/dept/tree_list"       ;
    static  final String URL309 =    "/sys/dept/treedata"        ;
    static  final String URL310 =    "/sys/dept/treedataorg"     ;
    static  final String URL311 =    "/sys/dict"                 ;
    static  final String URL312 =    "/sys/dict/add"             ;
    static  final String URL313 =    "/sys/dict/adddo"           ;
    static  final String URL314 =    "/sys/dict/edit/?"          ;
    static  final String URL315 =    "/sys/dict/editdo"          ;
    static  final String URL316 =    "/sys/dict/list"            ;
    static  final String URL317 =    "/sys/dict/remove"          ;
    static  final String URL318 =    "/sys/main"                 ;
    static  final String URL319 =    "/sys/menu"                 ;
    static  final String URL320 =    "/sys/menu/add/?"           ;
    static  final String URL321 =    "/sys/menu/adddo"           ;
    static  final String URL322 =    "/sys/menu/checkmenuunique" ;
    static  final String URL323 =    "/sys/menu/edit/?"          ;
    static  final String URL324 =    "/sys/menu/editdo"          ;
    static  final String URL325 =    "/sys/menu/list"            ;
    static  final String URL326 =    "/sys/menu/menutreedata"    ;
    static  final String URL327 =    "/sys/menu/remove/?"        ;
    static  final String URL328 =    "/sys/menu/rolemenutreedata" ;
    static  final String URL329 =    "/sys/menu/selectTree/?"    ;
    static  final String URL330 =    "/sys/post"                 ;
    static  final String URL331 =    "/sys/post/add"             ;
    static  final String URL332 =    "/sys/post/adddo"           ;
    static  final String URL333 =    "/sys/post/edit/?"          ;
    static  final String URL334 =    "/sys/post/editdo"          ;
    static  final String URL335 =    "/sys/post/list"            ;
    static  final String URL336 =    "/sys/post/remove"          ;
    static  final String URL337 =    "/sys/role"                 ;
    static  final String URL338 =    "/sys/role/add"             ;
    static  final String URL339 =    "/sys/role/adddo"           ;
    static  final String URL340 =    "/sys/role/checkrolenameunique" ;
    static  final String URL341 =    "/sys/role/edit/?"          ;
    static  final String URL342 =    "/sys/role/editdo"          ;
    static  final String URL343 =    "/sys/role/list"            ;
    static  final String URL344 =    "/sys/role/remove"          ;
    static  final String URL345 =    "/sys/task"                 ;
    static  final String URL346 =    "/sys/task/add"             ;
    static  final String URL347 =    "/sys/task/adddo"           ;
    static  final String URL348 =    "/sys/task/edit/?"          ;
    static  final String URL349 =    "/sys/task/editdo"          ;
    static  final String URL350 =    "/sys/task/list"            ;
    static  final String URL351 =    "/sys/task/remove"          ;
    static  final String URL352 =    "/sys/user"                 ;
    static  final String URL353 =    "/sys/user/add"             ;
    static  final String URL354 =    "/sys/user/adddo"           ;
    static  final String URL355 =    "/sys/user/checkloginnameunique" ;
    static  final String URL356 =    "/sys/user/edit/?"          ;
    static  final String URL357 =    "/sys/user/editdo"          ;
    static  final String URL358 =    "/sys/user/list"            ;
    static  final String URL359 =    "/sys/user/profile"         ;
    static  final String URL360 =    "/sys/user/profile/checkpassword" ;
    static  final String URL361 =    "/sys/user/profile/edit"    ;
    static  final String URL362 =    "/sys/user/profile/headPortrait" ;
    static  final String URL363 =    "/sys/user/profile/resetPwd" ;
    static  final String URL364 =    "/sys/user/profile/resetpwddo" ;
    static  final String URL365 =    "/sys/user/profile/update"  ;
    static  final String URL366 =    "/sys/user/profile/updateavatar" ;
    static  final String URL367 =    "/sys/user/remove"          ;
    static  final String URL368 =    "/sys/user/resetpwd"        ;
    static  final String URL369 =    "/sys/user/resetPwd/?"      ;
    static  final String URL370 =    "/tool/build"               ;
    static  final String URL371 =    "/tool/gen"                 ;
    static  final String URL372 =    "/tool/gen/genCode/?"       ;
    static  final String URL373 =    "/tool/gen/genTreeCode/?"   ;
    static  final String URL374 =    "/tool/gen/list"            ;
    static  final String URL375 =    "/tool/swagger"             ;
    static  final String URL376 =    "/topo//add_tag"            ;
    static  final String URL377 =    "/topo//check_tag"          ;
    static  final String URL378 =    "/topo//del_tag"            ;
    static  final String URL379 =    "/topo//dept_topo_base"     ;
    static  final String URL380 =    "/topo//devices_in_tag"     ;
    static  final String URL381 =    "/topo//devices_not_in_tag" ;
    //static  final String URL382 =    "/topo//full_topo"          ;
    static  final String URL383 =    "/topo//list_tag"           ;
    static  final String URL384 =    "/topo//pop_device_tag"     ;
    static  final String URL385 =    "/topo//push_device_tag"    ;
    static  final String URL386 =    "/topo//save_topo"          ;
    static  final String URL387 =    "/topo//save_topo"          ;
    static  final String URL388 =    "/topo//view_topo"          ;
    static  final String URL389 =    "/wx/menu"                  ;
    static  final String URL390 =    "/wx/menu/add" ;
    static  final String URL391 =    "/wx/menu/adddo"            ;
    static  final String URL392 =    "/wx/menu/edit/?"           ;
    static  final String URL393 =    "/wx/menu/editdo"           ;
    static  final String URL394 =    "/wx/menu/list"             ;
    static  final String URL395 =    "/wx/menu/pushmenu"         ;
    static  final String URL396 =    "/wx/menu/remove/?"         ;
    static  final String URL397 =    "/wx/menu/selectTree/?"     ;
    static  final String URL398 =    "/wx/menu/treedata"         ;
    static  final String URL399 =    "/wx/otherCase"             ;
    static  final String URL400 =    "/wx/otherCase/add"         ;
    static  final String URL401 =    "/wx/otherCase/adddo"       ;
    static  final String URL402 =    "/wx/otherCase/edit/?"      ;
    static  final String URL403 =    "/wx/otherCase/editdo"      ;
    static  final String URL404 =    "/wx/otherCase/list"        ;
    static  final String URL405 =    "/wx/otherCase/remove"      ;
    static  final String URL406 =    "/wx/otherEmp"              ;
    static  final String URL407 =    "/wx/otherEmp/add"          ;
    static  final String URL408 =    "/wx/otherEmp/adddo"        ;
    static  final String URL409 =    "/wx/otherEmp/edit/?"       ;
    static  final String URL410 =    "/wx/otherEmp/editdo"       ;
    static  final String URL411 =    "/wx/otherEmp/list"         ;
    static  final String URL412 =    "/wx/otherEmp/remove"       ;
    static  final String URL413 =    "/wx/otherWorkflow"         ;
    static  final String URL414 =    "/wx/otherWorkflow/add"     ;
    static  final String URL415 =    "/wx/otherWorkflow/adddo"   ;
    static  final String URL416 =    "/wx/otherWorkflow/edit/?"  ;
    static  final String URL417 =    "/wx/otherWorkflow/editdo"  ;
    static  final String URL418 =    "/wx/otherWorkflow/list"    ;
    static  final String URL419 =    "/wx/otherWorkflow/remove"  ;
    static  final String URL420 =    "/wx/tIotDevices"           ;
    static  final String URL421 =    "/wx/tIotDevices/add"       ;
    static  final String URL422 =    "/wx/tIotDevices/adddo"     ;
    static  final String URL423 =    "/wx/tIotDevices/change"    ;
    static  final String URL424 =    "/wx/tIotDevices/count"     ;
    static  final String URL425 =    "/wx/tIotDevices/edit/?"    ;
    static  final String URL426 =    "/wx/tIotDevices/editdo"    ;
    static  final String URL427 =    "/wx/tIotDevices/group"     ;
    static  final String URL428 =    "/wx/tIotDevices/list"      ;
    static  final String URL429 =    "/wx/tIotDevices/money"     ;
    static  final String URL430 =    "/wx/tIotDevices/out_time"  ;
    static  final String URL431 =    "/wx/tIotDevices/remove"    ;
    static  final String URL432 =    "/wx/tIotDevopsReason"      ;
    static  final String URL433 =    "/wx/tIotDevopsReason/add"  ;
    static  final String URL434 =    "/wx/tIotDevopsReason/adddo" ;
    static  final String URL435 =    "/wx/tIotDevopsReason/edit/?" ;
    static  final String URL436 =    "/wx/tIotDevopsReason/editdo" ;
    static  final String URL437 =    "/wx/tIotDevopsReason/list" ;
    static  final String URL438 =    "/wx/tIotDevopsReason/remove" ;
    static  final String URL439 =    "/wx/tIotOwner"             ;
    static  final String URL440 =    "/wx/tIotOwner/add"         ;
    static  final String URL441 =    "/wx/tIotOwner/adddo"       ;
    static  final String URL442 =    "/wx/tIotOwner/edit/?"      ;
    static  final String URL443 =    "/wx/tIotOwner/editdo"      ;
    static  final String URL444 =    "/wx/tIotOwner/list"        ;
    static  final String URL445 =    "/wx/tIotOwner/remove"      ;
    static  final String URL446 =    "/wx/tOtherMessages"        ;
    static  final String URL447 =    "/wx/tOtherMessages/add"    ;
    static  final String URL448 =    "/wx/tOtherMessages/adddo"  ;
    static  final String URL449 =    "/wx/tOtherMessages/change" ;
    static  final String URL450 =    "/wx/tOtherMessages/edit/?" ;
    static  final String URL451 =    "/wx/tOtherMessages/editdo" ;
    static  final String URL452 =    "/wx/tOtherMessages/list"   ;
    static  final String URL453 =    "/wx/tOtherMessages/messages" ;
    static  final String URL454 =    "/wx/tOtherMessages/remove" ;
    static  final String URL455 =    "/wx/tOtherReaStart"        ;
    static  final String URL456 =    "/wx/tOtherReaStart/add"    ;
    static  final String URL457 =    "/wx/tOtherReaStart/adddo"  ;
    static  final String URL458 =    "/wx/tOtherReaStart/change" ;
    static  final String URL459 =    "/wx/tOtherReaStart/edit/?" ;
    static  final String URL460 =    "/wx/tOtherReaStart/editdo" ;
    static  final String URL461 =    "/wx/tOtherReaStart/list"   ;
    static  final String URL462 =    "/wx/tOtherReaStart/messages" ;
    static  final String URL463 =    "/wx/tOtherReaStart/remove" ;
    static  final String URL464 =    "/wx/tOtherRemind"          ;
    static  final String URL465 =    "/wx/tOtherRemind/messages" ;
    static  final String URL466 =    "/wx/tTopoBases"            ;
    static  final String URL467 =    "/wx/tTopoBases/add"        ;
    static  final String URL468 =    "/wx/tTopoBases/adddo"      ;
    static  final String URL469 =    "/wx/tTopoBases/edit/?"     ;
    static  final String URL470 =    "/wx/tTopoBases/editdo"     ;
    static  final String URL471 =    "/wx/tTopoBases/list"       ;
    static  final String URL472 =    "/wx/tTopoBases/remove"     ;
    static  final String URL473 =    "/wx/tUiMetrics"            ;
    static  final String URL474 =    "/wx/tUiMetrics/add"        ;
    static  final String URL475 =    "/wx/tUiMetrics/adddo"      ;
    static  final String URL476 =    "/wx/tUiMetrics/change"     ;
    static  final String URL477 =    "/wx/tUiMetrics/edit/?"     ;
    static  final String URL478 =    "/wx/tUiMetrics/editdo"     ;
    static  final String URL479 =    "/wx/tUiMetrics/export"     ;
    static  final String URL480 =    "/wx/tUiMetrics/list"       ;
    static  final String URL481 =    "/wx/tUiMetrics/remove"     ;
    static  final String URL482 =    "/wx/wxUser"                ;
    static  final String URL483 =    "/wx/wxUser/down"           ;
    static  final String URL484 =    "/wx/wxUser/list"           ;


    @At(value = {
            URL100
            ,URL101
            ,URL102
            ,URL103
            ,URL104
            ,URL105
            ,URL106
            ,URL107
            ,URL108
            ,URL109
            ,URL110
            ,URL111
            ,URL112
            ,URL113
            ,URL114
            ,URL115
            ,URL116
            ,URL117
            ,URL118
            ,URL119
            ,URL120
            ,URL121
            ,URL122
            ,URL123
            ,URL124
            ,URL125
            ,URL126
            ,URL127
            ,URL128
            ,URL129
            ,URL130
            ,URL131
            ,URL132
            ,URL133
            ,URL134
            ,URL135
            ,URL136
            ,URL137
            ,URL138
            ,URL139
            ,URL140
            ,URL141
            ,URL142
            ,URL143
            ,URL144
            ,URL145
            ,URL146
            ,URL147
            ,URL148
            ,URL149
            ,URL150
            ,URL151
            ,URL152
            ,URL153
            ,URL154
            ,URL155
            ,URL156
            ,URL157
            ,URL158
            ,URL159
            ,URL160
            ,URL161
            ,URL162
            ,URL163
            ,URL164
            ,URL165
            ,URL166
            ,URL167
            ,URL168
            ,URL169
            ,URL170
            ,URL171
            ,URL172
            ,URL173
            ,URL174
            ,URL175
            ,URL176
            ,URL177
            ,URL178
            ,URL179
            ,URL180
            ,URL181
            ,URL182
            ,URL183
            ,URL184
            ,URL185
            ,URL186
            ,URL187
            ,URL188
            ,URL189
            ,URL190
            ,URL191
            ,URL192
            ,URL193
            ,URL194
            ,URL195
            ,URL196
            ,URL197
            ,URL198
            ,URL199
            ,URL200
            ,URL201
            ,URL202
            ,URL203
            ,URL204
            ,URL205
            ,URL206
            ,URL207
            ,URL208
            ,URL209
            ,URL210
            ,URL211
            ,URL212
            ,URL213
            ,URL214
            ,URL215
            ,URL216
            ,URL217
            ,URL218
            ,URL219
            ,URL220
            ,URL221
            ,URL222
            ,URL223
            ,URL224
            ,URL225
            ,URL226
            ,URL227
            ,URL228
            ,URL229
            ,URL230
            ,URL231
            ,URL232
            ,URL233
            ,URL234
            ,URL235
            ,URL236
            ,URL237
            ,URL238
            ,URL239
            ,URL240
            ,URL241
            ,URL242
            ,URL243
            ,URL244
            ,URL245
            ,URL246
            ,URL247
            ,URL248
            ,URL249
            ,URL250
            ,URL251
            ,URL252
            ,URL253
            ,URL254
            ,URL255
            ,URL256
            ,URL257
            ,URL258
            ,URL259
            ,URL260
            ,URL261
            ,URL262
            ,URL263
            ,URL264
            ,URL265
            ,URL266
            ,URL267
            ,URL268
            ,URL269
            ,URL270
            ,URL271
            ,URL272
            ,URL273
            ,URL274
            ,URL275
            ,URL276
            ,URL277
            ,URL278
            ,URL279
            ,URL280
            ,URL281
            ,URL282
            ,URL283
            ,URL284
            ,URL285
            ,URL286
            ,URL287
            ,URL288
            ,URL289
            ,URL290
            ,URL291
            ,URL292
            ,URL293
            ,URL294
            ,URL295
            ,URL296
            ,URL297
            ,URL298
            ,URL299
            ,URL300
            ,URL301
            ,URL302
            ,URL303
            ,URL304
            ,URL305
            ,URL306
            ,URL307
            ,URL308
            ,URL309
            ,URL310
            ,URL311
            ,URL312
            ,URL313
            ,URL314
            ,URL315
            ,URL316
            ,URL317
            ,URL318
            ,URL319
            ,URL320
            ,URL321
            ,URL322
            ,URL323
            ,URL324
            ,URL325
            ,URL326
            ,URL327
            ,URL328
            ,URL329
            ,URL330
            ,URL331
            ,URL332
            ,URL333
            ,URL334
            ,URL335
            ,URL336
            ,URL337
            ,URL338
            ,URL339
            ,URL340
            ,URL341
            ,URL342
            ,URL343
            ,URL344
            ,URL345
            ,URL346
            ,URL347
            ,URL348
            ,URL349
            ,URL350
            ,URL351
            ,URL352
            ,URL353
            ,URL354
            ,URL355
            ,URL356
            ,URL357
            ,URL358
            ,URL359
            ,URL360
            ,URL361
            ,URL362
            ,URL363
            ,URL364
            ,URL365
            ,URL366
            ,URL367
            ,URL368
            ,URL369
            ,URL370
            ,URL371
            ,URL372
            ,URL373
            ,URL374
            ,URL375
            ,URL376
            ,URL377
            ,URL378
            ,URL379
            ,URL380
            ,URL381
            //,URL382
            ,URL383
            ,URL384
            ,URL385
            ,URL386
            ,URL387
            ,URL388
            ,URL389
            ,URL390
            ,URL391
            ,URL392
            ,URL393
            ,URL394
            ,URL395
            ,URL396
            ,URL397
            ,URL398
            ,URL399
            ,URL400
            ,URL401
            ,URL402
            ,URL403
            ,URL404
            ,URL405
            ,URL406
            ,URL407
            ,URL408
            ,URL409
            ,URL410
            ,URL411
            ,URL412
            ,URL413
            ,URL414
            ,URL415
            ,URL416
            ,URL417
            ,URL418
            ,URL419
            ,URL420
            ,URL421
            ,URL422
            ,URL423
            ,URL424
            ,URL425
            ,URL426
            ,URL427
            ,URL428
            ,URL429
            ,URL430
            ,URL431
            ,URL432
            ,URL433
            ,URL434
            ,URL435
            ,URL436
            ,URL437
            ,URL438
            ,URL439
            ,URL440
            ,URL441
            ,URL442
            ,URL443
            ,URL444
            ,URL445
            ,URL446
            ,URL447
            ,URL448
            ,URL449
            ,URL450
            ,URL451
            ,URL452
            ,URL453
            ,URL454
            ,URL455
            ,URL456
            ,URL457
            ,URL458
            ,URL459
            ,URL460
            ,URL461
            ,URL462
            ,URL463
            ,URL464
            ,URL465
            ,URL466
            ,URL467
            ,URL468
            ,URL469
            ,URL470
            ,URL471
            ,URL472
            ,URL473
            ,URL474
            ,URL475
            ,URL476
            ,URL477
            ,URL478
            ,URL479
            ,URL480
            ,URL481
            ,URL482
            ,URL483
            ,URL484
    } ,methods = "OPTIONS")
    public void sql( ){

    }



}
