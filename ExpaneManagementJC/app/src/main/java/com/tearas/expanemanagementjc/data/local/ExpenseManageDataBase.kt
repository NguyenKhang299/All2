package com.tearas.expanemanagementjc.data.local

import android.accounts.AccountManager
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tearas.expanemanagementjc.domain.model.Account
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.Expense
import com.tearas.expanemanagementjc.domain.model.RemindModel
import javax.inject.Inject

@Database(
    entities = [Account::class, Expense::class, Category::class, CategoryImage::class, RemindModel::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ExpenseMangerTypeConverter::class)
abstract class ExpenseManageDataBase : RoomDatabase() {
    abstract fun categoryDAO(): CategoryDAO
    abstract fun categoryImageDAO(): CategoryImageDAO
    abstract fun expenseDAO(): ExpenseDAO
    abstract fun accountDAO(): AccountDAO
    abstract fun statisticalDAO(): StatisticalDAO
    abstract fun remindDAO(): RemindDAO
}