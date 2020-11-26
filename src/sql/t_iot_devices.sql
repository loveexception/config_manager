update  t_iot_devices 
set  order_time =unix_timestamp( create_time)
where order_time is null  or order_time ='' or order_time = '0';

update  t_iot_devices 
set  order_time = unix_timestamp(update_time)
where order_time is null or order_time =''  or order_time='0' ;

UPDATE t_iot_devices 
set order_time = unix_timestamp(order_time)
where LENGTH(order_time) < 14 and (order_time REGEXP '[^0-9.]');

UPDATE t_iot_devices 
set order_time = unix_timestamp(order_time)
where LENGTH(order_time) < 7 and order_time like '19%'