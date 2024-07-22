package com.tearas.expanemanagementjc.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tearas.expanemanagementjc.data.dto.CategoryDto
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage
import com.tearas.expanemanagementjc.domain.model.CategoryType
import com.tearas.expanemanagementjc.domain.model.TransactionType
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(vararg category: Category): kotlin.collections.List<kotlin.Long>

    @Update
    suspend fun updateCategory(category: Category): Int


    @Query("SELECT * FROM category where category.category_id=:idCategory")
    suspend fun selectCategory(idCategory: Long): Category

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query(
        """
            SELECT category_id,category,path_icon,category.transaction_type,isDefault,isDelete  FROM category 
           inner join category_image on category.category_image_id==category_image.category_image_id 
           WHERE category.transaction_type = :type """
    )
    fun selectCategoriesDto(type: TransactionType): Flow<List<CategoryDto>>

    @Query(
        """
           SELECT category_id,category,path_icon,transaction_type,isDefault,isDelete  FROM category 
           inner join category_image on category.category_image_id==category_image.category_image_id 
          """
    )
    fun selectAllCategoriesDto(): Flow<List<CategoryDto>>


    @Query(
        """
           SELECT  *  FROM category_image 
           """
    )
    fun selectMapCategoriesImage(): Flow<List<CategoryImage>>
}
