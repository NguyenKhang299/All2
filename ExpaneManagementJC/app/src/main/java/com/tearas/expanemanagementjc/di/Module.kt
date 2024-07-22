package com.tearas.expanemanagementjc.di

import android.app.AlarmManager
import android.app.Application
import androidx.room.Room
import com.tearas.expanemanagementjc.data.local.AccountDAO
import com.tearas.expanemanagementjc.data.local.CategoryDAO
import com.tearas.expanemanagementjc.data.local.CategoryImageDAO
import com.tearas.expanemanagementjc.data.local.ExpenseDAO
import com.tearas.expanemanagementjc.data.local.ExpenseManageDataBase
import com.tearas.expanemanagementjc.data.local.ExpenseMangerTypeConverter
import com.tearas.expanemanagementjc.data.local.RemindDAO
import com.tearas.expanemanagementjc.data.local.StatisticalDAO
import com.tearas.expanemanagementjc.data.manager.LocalUserManager
import com.tearas.expanemanagementjc.data.manager.SettingManagerImpl
import com.tearas.expanemanagementjc.data.repository.AccountRepositoryImpl
import com.tearas.expanemanagementjc.data.repository.CategoryImageRepositoryImpl
import com.tearas.expanemanagementjc.data.repository.CategoryRepositoryImpl
import com.tearas.expanemanagementjc.data.repository.ExpenseRepositoryImpl
import com.tearas.expanemanagementjc.data.repository.RemindRepositoryImpl
import com.tearas.expanemanagementjc.data.repository.StatisticalRepositoryImpl
import com.tearas.expanemanagementjc.service.AlarmManagerService
import com.tearas.expanemanagementjc.service.ExcelService
import com.tearas.expanemanagementjc.usecases.account.AccountUseCases
import com.tearas.expanemanagementjc.usecases.account.InsertAccount
import com.tearas.expanemanagementjc.usecases.account.SelectAccountUsing
import com.tearas.expanemanagementjc.usecases.category.CategoryUseCases
import com.tearas.expanemanagementjc.usecases.category.DeleteCategory
import com.tearas.expanemanagementjc.usecases.category.InsertCategory
import com.tearas.expanemanagementjc.usecases.category.SelectCategoriesDto
import com.tearas.expanemanagementjc.usecases.category.SelectCategory
import com.tearas.expanemanagementjc.usecases.category.SelectMapCategoriesImage
import com.tearas.expanemanagementjc.usecases.category.UpdateCategory
import com.tearas.expanemanagementjc.usecases.category_image.CategoryImageUseCase
import com.tearas.expanemanagementjc.usecases.category_image.InsertCategoryImage
import com.tearas.expanemanagementjc.usecases.category_image.SelectCategoryImage
import com.tearas.expanemanagementjc.usecases.category_image.SelectCategoryImages
import com.tearas.expanemanagementjc.usecases.excel.ExcelUseCases
import com.tearas.expanemanagementjc.usecases.excel.ExportExcel
import com.tearas.expanemanagementjc.usecases.expense.DeleteExpense
import com.tearas.expanemanagementjc.usecases.expense.ExpenseUseCases
import com.tearas.expanemanagementjc.usecases.expense.InsertExpense
import com.tearas.expanemanagementjc.usecases.expense.SearchExpense
import com.tearas.expanemanagementjc.usecases.expense.SelectExpense
import com.tearas.expanemanagementjc.usecases.expense.SelectExpenses
import com.tearas.expanemanagementjc.usecases.expense.UpdateExpense
import com.tearas.expanemanagementjc.usecases.manager_user.DeletePassword
import com.tearas.expanemanagementjc.usecases.manager_user.GetPassword
import com.tearas.expanemanagementjc.usecases.manager_user.LocalUserUseCases
import com.tearas.expanemanagementjc.usecases.manager_user.SetPassword
import com.tearas.expanemanagementjc.usecases.remind.DeleteRemind
import com.tearas.expanemanagementjc.usecases.remind.GetRemind
import com.tearas.expanemanagementjc.usecases.remind.GetReminds
import com.tearas.expanemanagementjc.usecases.remind.InsertRemind
import com.tearas.expanemanagementjc.usecases.remind.RemindUseCases
import com.tearas.expanemanagementjc.usecases.remind.UpdateRemind
import com.tearas.expanemanagementjc.usecases.setting.GetSetting
import com.tearas.expanemanagementjc.usecases.setting.SetSetting
import com.tearas.expanemanagementjc.usecases.setting.SettingUseCases
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalDetailsCategoryByMonth
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalDetailsCategoryByYear
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalMonth
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalReportExpenses
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalUseCases
import com.tearas.expanemanagementjc.usecases.statistcal.StatisticalYear
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    @Singleton
    fun provideExpenseDataBase(application: Application) = Room.databaseBuilder(
        application,
        ExpenseManageDataBase::class.java,
        "ExpenseManageDataBase"
    ).addTypeConverter(ExpenseMangerTypeConverter())
        .fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideExpenseDao(expenseManageDataBase: ExpenseManageDataBase) =
        expenseManageDataBase.expenseDAO()

    @Provides
    @Singleton
    fun provideCategoryImageDao(expenseManageDataBase: ExpenseManageDataBase) =
        expenseManageDataBase.categoryImageDAO()

    @Provides
    @Singleton
    fun provideCategoryDao(expenseManageDataBase: ExpenseManageDataBase) =
        expenseManageDataBase.categoryDAO()

    @Provides
    @Singleton
    fun provideAccountDao(expenseManageDataBase: ExpenseManageDataBase) =
        expenseManageDataBase.accountDAO()

    @Provides
    @Singleton
    fun provideStatisticalDao(expenseManageDataBase: ExpenseManageDataBase) =
        expenseManageDataBase.statisticalDAO()

    @Provides
    @Singleton
    fun provideRemindDao(expenseManageDataBase: ExpenseManageDataBase) =
        expenseManageDataBase.remindDAO()

    @Provides
    @Singleton
    fun provideLocalUserManager(application: Application) = LocalUserManager(application)

    @Provides
    @Singleton
    fun provideSettingManagerImpl(application: Application) = SettingManagerImpl(application)

    @Provides
    @Singleton
    fun provideAccountRepository(accountDAO: AccountDAO) = AccountRepositoryImpl(accountDAO)

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDAO: ExpenseDAO) = ExpenseRepositoryImpl(expenseDAO)

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryDAO: CategoryDAO) = CategoryRepositoryImpl(categoryDAO)

    @Provides
    @Singleton
    fun provideCategoryImageRepository(categoryImageDAO: CategoryImageDAO) =
        CategoryImageRepositoryImpl(categoryImageDAO)

    @Provides
    @Singleton
    fun provideStatisticalRepository(statisticalDAO: StatisticalDAO) =
        StatisticalRepositoryImpl(statisticalDAO)

    @Provides
    @Singleton
    fun provideRemindRepository(remindDAO: RemindDAO, alarmManager: AlarmManagerService) =
        RemindRepositoryImpl(remindDAO, alarmManager)

    @Provides
    @Singleton
    fun provideAccountUseCases(accountRepositoryImpl: AccountRepositoryImpl) = AccountUseCases(
        insertAccount = InsertAccount(accountRepositoryImpl),
        selectAccountUsing = SelectAccountUsing(accountRepositoryImpl)
    )


    @Provides
    @Singleton
    fun provideExpenseUseCases(repositoryImpl: ExpenseRepositoryImpl) = ExpenseUseCases(
        selectExpense = SelectExpense(repositoryImpl),
        selectExpenses = SelectExpenses(repositoryImpl),
        insertExpense = InsertExpense(repositoryImpl),
        deleteExpense = DeleteExpense(repositoryImpl),
        updateExpense = UpdateExpense(repositoryImpl),
        searchExpense = SearchExpense(repositoryImpl)
    )

    @Provides
    @Singleton
    fun provideCategoryUseCases(repositoryImpl: CategoryRepositoryImpl) = CategoryUseCases(
        deleteCategory = DeleteCategory(repositoryImpl),
        insertCategory = InsertCategory(repositoryImpl),
        selectCategoriesDto = SelectCategoriesDto(repositoryImpl),
        updateCategory = UpdateCategory(repositoryImpl),
        selectCategory = SelectCategory(repositoryImpl),
        selectMapCategoriesImage = SelectMapCategoriesImage(repositoryImpl)
    )

    @Provides
    @Singleton
    fun provideCategoryImageUseCases(repositoryImpl: CategoryImageRepositoryImpl) =
        CategoryImageUseCase(
            selectedCategoryImage = SelectCategoryImage(repositoryImpl),
            insertCategoryImage = InsertCategoryImage(repositoryImpl),
            selectedCategoryImages = SelectCategoryImages(repositoryImpl)
        )

    @Provides
    @Singleton
    fun provideStatisticalUseCases(repositoryImpl: StatisticalRepositoryImpl) =
        StatisticalUseCases(
            statisticalMonth = StatisticalMonth(repositoryImpl),
            statisticalYear = StatisticalYear(repositoryImpl),
            statisticalDetailsCategoryByMonth = StatisticalDetailsCategoryByMonth(repositoryImpl),
            statisticalDetailsCategoryByYear = StatisticalDetailsCategoryByYear(repositoryImpl),
            statisticalReportExpenses = StatisticalReportExpenses(repositoryImpl)
        )

    @Provides
    @Singleton
    fun provideRemindUseCases(repositoryImpl: RemindRepositoryImpl) =
        RemindUseCases(
            deleteRemind = DeleteRemind(repositoryImpl),
            insertRemind = InsertRemind(repositoryImpl),
            updateRemind = UpdateRemind(repositoryImpl),
            getRemind = GetRemind(repositoryImpl),
            getReminds = GetReminds(repositoryImpl)
        )

    @Provides
    @Singleton
    fun provideSettingUseCases(repositoryImpl: SettingManagerImpl) =
        SettingUseCases(
            getSetting = GetSetting(repositoryImpl),
            setSetting = SetSetting(repositoryImpl)
        )

    @Provides
    @Singleton
    fun provideExcelService(application: Application) = ExcelService(application)


    @Provides
    @Singleton
    fun provideExcelUseCases(excelService: ExcelService) =
        ExcelUseCases(
            exportExcel = ExportExcel(excelService)
        )

    @Provides
    @Singleton
    fun provideLocalUserUseCases(localUserManager: LocalUserManager) = LocalUserUseCases(
        setPassword = SetPassword(localUserManager),
        getPassword = GetPassword(localUserManager),
        deletePassword = DeletePassword(localUserManager)
    )
}