package com.tearas.expanemanagementjc.data.local

import androidx.room.Dao
import androidx.room.Query
import com.tearas.expanemanagementjc.data.dto.ExpenseDto
import com.tearas.expanemanagementjc.data.dto.StatisticalDto
import com.tearas.expanemanagementjc.domain.model.ProfitLoss
import com.tearas.expanemanagementjc.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface StatisticalDAO {
    @Query(
        """
        SELECT 
           *,
    SUM(e.expense) AS total_expense, 
        ROUND((SUM(e.expense) * 100.0 / (SELECT SUM(expense) FROM expense WHERE transaction_type = :transactionType AND strftime('%Y', datetime(date / 1000, 'unixepoch')) = :year AND strftime('%m', datetime(date / 1000, 'unixepoch')) = :month)), 2) AS percentage
        FROM 
            category c
        INNER JOIN 
            category_image ci 
        ON 
            c.category_image_id = ci.category_image_id
        INNER JOIN 
            expense e 
        ON 
            e.category_id = c.category_id
        WHERE 
            c.transaction_type = :transactionType 
      AND strftime('%Y', datetime(e.date / 1000, 'unixepoch')) = :year 
      AND strftime('%m', datetime(e.date / 1000, 'unixepoch')) = :month 
        GROUP BY 
            c.category_id    
        ORDER BY 
            percentage DESC
        """
    )
    suspend fun statisticalMonth(
        transactionType: TransactionType,
        month: String,
        year: String
    ): List<StatisticalDto>

    @Query(
        """
        SELECT 
           *,
    SUM(e.expense) AS total_expense, 
        ROUND((SUM(e.expense) * 100.0 / (SELECT SUM(expense) FROM expense WHERE transaction_type = :transactionType AND strftime('%Y', datetime(date / 1000, 'unixepoch')) = :year )), 2) AS percentage
        FROM 
            category c
        INNER JOIN 
            category_image ci 
        ON 
            c.category_image_id = ci.category_image_id
        INNER JOIN 
            expense e 
        ON 
            e.category_id = c.category_id
        WHERE 
            e.transaction_type = :transactionType 
      AND strftime('%Y', datetime(e.date / 1000, 'unixepoch')) = :year 
         GROUP BY 
            c.category_id    
        ORDER BY 
            percentage DESC
        """
    )
    suspend fun statisticalYear(
        transactionType: TransactionType,
        year: String
    ): List<StatisticalDto>


    @Query(
        """
    SELECT 
         *,
        e.expense AS total_expense, 
        ROUND((e.expense * 100.0 / 
            (SELECT SUM(expense) 
             FROM expense 
             WHERE 
                 transaction_type = :transactionType 
                 AND strftime('%Y', datetime(date / 1000, 'unixepoch')) = :year
                 AND category_id = :idCategory)), 2) AS percentage
    FROM 
        category c
    INNER JOIN 
        category_image ci 
    ON 
        c.category_image_id = ci.category_image_id
    INNER JOIN 
        expense e 
    ON 
        e.category_id = c.category_id
    WHERE 
        e.transaction_type = :transactionType 
        AND strftime('%Y', datetime(e.date / 1000, 'unixepoch')) = :year
        AND e.category_id = :idCategory
    """
    )
    suspend fun statisticalDetailsCategoryByYear(
        idCategory: Long,
        transactionType: TransactionType,
        year: String
    ): List<StatisticalDto>

    @Query(
        """
    SELECT 
         *,isDefault,
        e.expense AS total_expense, 
        ROUND((e.expense * 100.0 / 
            (SELECT SUM(expense) 
             FROM expense 
             WHERE 
                 transaction_type = :transactionType 
                 AND strftime('%Y', datetime(date / 1000, 'unixepoch')) = :year
                 AND strftime('%m', datetime(date / 1000, 'unixepoch')) = :month 
                 AND category_id = :idCategory)), 2) AS percentage
    FROM 
        category c
    INNER JOIN 
        category_image ci 
    ON 
        c.category_image_id = ci.category_image_id
    INNER JOIN 
        expense e 
    ON 
        e.category_id = c.category_id
    WHERE 
        e.transaction_type = :transactionType 
        AND strftime('%Y', datetime(e.date / 1000, 'unixepoch')) = :year
        AND strftime('%m', datetime(e.date / 1000, 'unixepoch')) = :month 
        AND e.category_id = :idCategory
    """
    )
    suspend fun statisticalDetailsCategoryByMonth(
        idCategory: Long,
        transactionType: TransactionType,
        month: String,
        year: String
    ): List<StatisticalDto>

    @Query(
        """
    SELECT  date ,
    strftime('%m', datetime(expense.date / 1000, 'unixepoch')) as month,
    strftime('%Y', datetime(expense.date / 1000, 'unixepoch')) as year,
    SUM(CASE WHEN  expense.transaction_type = 'COLLECT' THEN  expense.expense ELSE 0 END) as collect,
    SUM(CASE WHEN  expense.transaction_type = 'SPEND' THEN  expense.expense ELSE 0 END) as spend,
    SUM(CASE WHEN  expense.transaction_type = 'SPEND' THEN  -expense.expense  ELSE expense.expense END) as surplus
    FROM expense
    INNER JOIN account ON expense.account_id = account.account_id
    WHERE account.isUsing = 1  
    GROUP BY month, year
    ORDER BY expense.date ASC
    """
    )
    fun statisticalReportExpenses(): Flow<List<ProfitLoss>>

}