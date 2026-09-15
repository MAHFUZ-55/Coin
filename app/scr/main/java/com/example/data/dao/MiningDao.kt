package com.example.data.dao

import androidx.room.*
import com.example.data.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MiningDao {
    // User operations
    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    fun getUserFlow(userId: Long = 1L): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :userId LIMIT 1")
    suspend fun getUser(userId: Long = 1L): UserEntity?

    @Query("SELECT * FROM users ORDER BY balance DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    @Query("UPDATE users SET balance = balance + :amount, totalEarned = totalEarned + :amount WHERE id = :userId")
    suspend fun addBalance(userId: Long, amount: Long)

    @Query("UPDATE users SET energy = :newEnergy, lastEnergyUpdate = :timestamp WHERE id = :userId")
    suspend fun updateEnergy(userId: Long, newEnergy: Int, timestamp: Long)

    // Tasks operations
    @Query("SELECT * FROM tasks WHERE isActive = 1")
    fun getActiveTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT taskId FROM task_completions WHERE userId = :userId")
    fun getCompletedTaskIds(userId: Long = 1L): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTaskCompletion(completion: TaskCompletionEntity)

    // Transactions
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTransactions(userId: Long = 1L): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    // Withdrawals
    @Query("SELECT * FROM withdrawals WHERE userId = :userId ORDER BY timestamp DESC")
    fun getWithdrawals(userId: Long = 1L): Flow<List<WithdrawalEntity>>

    @Query("SELECT * FROM withdrawals ORDER BY timestamp DESC")
    fun getAllWithdrawals(): Flow<List<WithdrawalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithdrawal(withdrawal: WithdrawalEntity): Long

    @Query("UPDATE withdrawals SET status = :status WHERE id = :id")
    suspend fun updateWithdrawalStatus(id: Long, status: String)

    // Referrals
    @Query("SELECT * FROM referrals WHERE referrerId = :userId ORDER BY joinedAt DESC")
    fun getReferrals(userId: Long = 1L): Flow<List<ReferralEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferral(referral: ReferralEntity)

    // Settings
    @Query("SELECT * FROM settings")
    fun getAllSettings(): Flow<List<SettingEntity>>

    @Query("SELECT value FROM settings WHERE `key` = :key LIMIT 1")
    suspend fun getSetting(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSetting(setting: SettingEntity)

    // Audit Logs
    @Query("SELECT * FROM audit_logs ORDER BY timestamp DESC LIMIT 50")
    fun getAuditLogs(): Flow<List<AuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AuditLogEntity)
}
