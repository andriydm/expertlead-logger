# expertlead-logger

## Performance

To improve the Logger's performance, asynchronous logging techniques were applied, such as batching log 
entries and using worker threads to handle them in the background. This reduces the overhead of logging 
on the main application thread and allows for faster response times.

### TimedBatchedLogTarget 

TimedBatchedLogTarget class wraps another Target implementation and adds batching 
functionality to it. The batchSize parameter determines the maximum number of log entries that 
can be buffered before they are flushed to the target. The timeIntervalSeconds parameter determines 
the time interval after which the buffered log entries are flushed.

The log method adds each log entry to the buffer. The flush task is scheduled at a fixed rate 
using a ScheduledExecutorService, which executes the flush method after every time interval. 
The flush method first checks if the buffer is empty and returns if it is. Otherwise, it takes 
a batch of log entries from the buffer and logs them to the target. Finally, the batch is removed 
from the buffer.

Also, the flush task is scheduled to run periodically to ensure that the buffered log entries are 
flushed even if the batchSize is not reached. The shutdown method is added to properly shutdown 
the executor service and flush any remaining buffered log entries.

### AsyncLogTarget

AsyncLogTarget class wraps another Target implementation and adds asynchronous functionality 
to it. The workerThreads parameter determines the number of worker threads used to handle log 
entries in the background. The BlockingQueue is used to buffer the log entries and the ExecutorService 
is used to manage the worker threads.

The log method adds each log entry to the queue. The worker threads continuously take log entries 
from the queue and log them to the target until the queue is empty. The worker threads are 
implemented using the Runnable interface and the LogWorker class. The run method of the LogWorker 
takes a log entry from the queue using the take method, which blocks until an entry is available. 
Then, it logs the entry to the target. The worker thread loop runs until the thread is interrupted.

### How we can made Logger more open to modifications

Using a factory method to create instances of Logger and its dependencies. This would allow us to 
encapsulate the creation logic and make it easier to modify the Logger's behavior without changing 
its code.

Make the Logger more configurable by providing options to enable/disable certain features or 
change the behavior of the Logger at runtime. This would allow clients to customize the Logger 
to their specific needs and requirements.

Design the Logger to be easily extendable by providing hooks or extension points 
that clients can use to add their own functionality or modify the behavior of the Logger. 
This would allow clients to adapt the Logger to their specific needs without having to modify its core code.
And also, by defining an interface for log targets and allows clients to provide their own implementations.