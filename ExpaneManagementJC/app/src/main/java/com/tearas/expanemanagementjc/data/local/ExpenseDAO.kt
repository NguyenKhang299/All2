package com.tearas.expanemanagementjc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.domain.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense): Long

    @Update
    suspend fun updateExpense(vararg expense: Expense)

    @Query("Delete from expense where expenseId=:expenseId")
    suspend fun deleteExpense(expenseId: Long)


    @Query(
        """
    SELECT path_icon,isDefault, path_file, note, expense.transaction_type, expense.expenseId, expense.category_id, category, expense, expense.date
    FROM expense
    INNER JOIN account ON expense.account_id = account.account_id
    INNER JOIN category ON category.category_id = expense.category_id
    INNER JOIN category_image ON category_image.category_image_id = category.category_image_id
    WHERE account.isUsing = 1 
      AND strftime('%Y', datetime(expense.date / 1000, 'unixepoch')) = :year 
      AND strftime('%m', datetime(expense.date / 1000, 'unixepoch')) = :month
    """
    )
    fun selectExpenses(month: String, year: String): Flow<List<ExpenseDto>>

    @Query(
        """
    SELECT path_icon,isDefault, path_file, note, expense.transaction_type, expense.expenseId, expense.category_id, category, expense, expense.date
    FROM expense
    INNER JOIN account ON expense.account_id = account.account_id
    INNER JOIN category ON category.category_id = expense.category_id
    INNER JOIN category_image ON category_image.category_image_id = category.category_image_id
    WHERE account.isUsing = 1 AND (:category != '' AND category LIKE '%' || :category || '%')
      
    """
    )
    fun searchExpenses(category: String): Flow<List<ExpenseDto>>

    @Query(
        """
    SELECT path_icon,isDefault,path_file,note,expense.transaction_type,expense.expenseId,expense.category_id,category,expense,expense.date 
    FROM expense
    INNER JOIN account ON expense.account_id = account.account_id
    INNER JOIN category ON category.category_id = expense.category_id
    INNER JOIN category_image ON category_image.category_image_id = category.category_image_id
    WHERE expense.expenseId = :expenseId 
     """
    )
    suspend fun selectExpense(expenseId: Long): ExpenseDto


}
