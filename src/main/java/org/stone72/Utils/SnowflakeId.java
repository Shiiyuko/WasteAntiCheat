package org.stone72.Utils;

public class SnowflakeId {
   private final long workerIdBits = 5L;
   private final long datacenterIdBits = 5L;
   private final long workerId;
   private final long datacenterId;
   private long sequence = 0L;
   private long startTimestamp;

   public SnowflakeId(long workerId, long datacenterId) {
      long maxWorkerId = 31L;
      if (workerId <= maxWorkerId && workerId >= 0L) {
         long maxDatacenterId = 31L;
         if (datacenterId <= maxDatacenterId && datacenterId >= 0L) {
            this.workerId = workerId;
            this.datacenterId = datacenterId;
            this.startTimestamp = System.currentTimeMillis();
         } else {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
         }
      } else {
         throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
      }
   }

   public synchronized long nextId() {
      long sequenceTmp = this.sequence;
      long sequenceBits = 12L;
      long sequenceMask = ~(-1L << (int)sequenceBits);
      this.sequence = this.sequence + 1L & sequenceMask;
      if (this.sequence == 0L && sequenceTmp >= 0L) {
         ++this.startTimestamp;
      }

      long twepoch = 1420041600000L;
      long timestampLeftShift = sequenceBits + 5L + 5L;
      long datacenterIdShift = sequenceBits + 5L;
      return this.startTimestamp - twepoch << (int)timestampLeftShift | this.datacenterId << (int)datacenterIdShift | this.workerId << (int)sequenceBits | this.sequence;
   }
}
