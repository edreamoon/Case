package com.ccino.demo.jetpack.room

import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ccino.demo.app
import com.ccnio.ware.jetpack.room.Trigger
import com.ccnio.ware.jetpack.room.TriggerDao
import com.ccnio.ware.jetpack.room.UserDao
import com.ccnio.ware.jetpack.room.UserEntity

/**
 * Created by ccino on 2021/10/12.
 */
private const val TAG_L = "RoomDb"

@Database(entities = [
    UserEntity::class,
    Trigger::class
    /*  Machine::class,
      Trigger2::class,
      TempTrigger::class*/
],
    version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {

    companion object {
        private val dbCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.d(TAG_L, "onCreate: ")
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                Trigger.createTrigger(db)
            }
        }

        //单例，每个RoomDatabase实例都相当消耗性能
        val roomDb = Room.databaseBuilder(app, RoomDb::class.java, "room_db")
            .addCallback(dbCallback).build()

        /**
         * 监听的数据插入冲突未执行时，不会执行 trigger 的语句
         *
         *  CREATE TRIGGER // 创建触发器
        auto_remove    // 触发器名称，后期可以用来查询和移除触发器
        BEFORE         // 在事件之前触发，改为AFTER就是之后触发
        INSERT         // 在插入事件触发，还支持DELETE、UPDATE
        ON db_list_table   // 操作哪个表
        BEGIN   // 触发语句开始
        // 触发语句，删除db_list_table表中和当前插入数据的user_id、item_id相同的数据
        DELETE FROM db_list_table WHERE user_id=NEW.user_id AND item_id=NEW.item_id;// 不要忘了分号
        // 因为触发事件是INSERT，所以表单数据要用NEW.column-name引用；
        // 可能比较绕，你品品，你细品，是不是很有道理(ಡωಡ)
        END;    // 触发语句结束
        复制代码NEW 和OLD 关键字的英文文档
        Both the WHEN clause and the trigger actions may access elements of the row being inserted, deleted or updated using references of the form "NEW.column-name" and "OLD.column-name",
        where column-name is the name of a column from the table that the trigger is associated with.
        OLD and NEW references may only be used in triggers on events for which they are relevant, as follows:

        INSERT	NEW references are valid    // 插入时NEW有效
        UPDATE	NEW and OLD references are valid    // 均有效
        DELETE	OLD references are valid    // 删除时OLD有效
        // 这里的INSERT，UPADATE，DELETE指的是触发动作类型，不是触发语句类型。就是BEFOR/AFTER后面的操作
        在 INSERT 型触发器中，NEW 用来表示将要（BEFORE）或已经（AFTER）插入的新数据；
        在 UPDATE 型触发器中，OLD 用来表示将要或已经被修改的原数据，NEW 用来表示将要或已经修改为的新数据；
        在 DELETE 型触发器中，OLD 用来表示将要或已经被删除的原数据；


        # when 语句中也支持查询: WHEN NEW.ID NOT IN (SELECT ID FROM LOG), 不支持 if else
        1. 字符需要加引号
         */

    }

    abstract fun userDao(): UserDao
    abstract fun triggerDao(): TriggerDao
//    abstract fun machineDao(): MachineDao
}