package com.tearas.expanemanagementjc.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tearas.expanemanagementjc.domain.model.Category
import com.tearas.expanemanagementjc.domain.model.CategoryImage

@Dao
interface CategoryImageDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryImg(vararg categoryImg: CategoryImage): List<Long>

    @Query("SELECT * FROM category_image")
    suspend fun selectCategoriesImg(): List<CategoryImage>

    @Query("SELECT * FROM category_image where category_image_id=:id")
    suspend fun selectCategoryImage(id: Long): CategoryImage
}