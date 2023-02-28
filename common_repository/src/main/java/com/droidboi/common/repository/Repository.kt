package com.droidboi.common.repository

import com.droidboi.common.repository.inMemory.InMemoryData

/**
 * Abstraction for a Repository of the application.
 *
 * @param MemoryData Any [InMemoryData] of this Repository.
 * @author Ritwik Jamuar
 */
interface Repository<MemoryData : InMemoryData> {

	/**
	 * Reference of [MemoryData] to manipulate the data held in memory.
	 */
	val inMemoryData: MemoryData

}
