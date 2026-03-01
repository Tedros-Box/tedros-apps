## Examples of how to call ejb controllers remotely

### List all users

```java
    try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
        
        TUserController controller = locator.lookup(TUserController.JNDI_NAME);
            
        TResult<List<TUser>> result = controller
        .listAll(TedrosContext.getLoggedUser().getAccessToken(), TUser.class);

        if(result.isSuccess()) {
			List<TUser> users = result.getValue();
		}
            
    } catch (Exception e) {
		TLoggerUtil.error(e.getMessage(), e);
	}
```

### List all employees

```java
    try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
        IEmployeeController controller = locator.lookup(IEmployeeController.JNDI_NAME);
            
        TResult<List<Employee>> result = controller.listAll(TedrosContext.getLoggedUser().getAccessToken(), Employee.class);
    
        if(result.isSuccess()) {
		    List<Employee> users = result.getValue();
	    }
            
    } catch (Exception e) {
	    TLoggerUtil.error(e.getMessage(), e);
	}
```

### Filter User activities

```java
    Long userId = 1L;
        
    try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
            IProductivityActivityController controller = locator.lookup(IProductivityActivityController.JNDI_NAME);
            TResult<List<ProductivityActivityDTO>> result = controller.findUserIdAndDateRange(TedrosContext.getLoggedUser().getAccessToken(), userId, LocalDateTime.now(), LocalDateTime.now().plusDays(1));
            if (result.isSuccess()) {
            	List<ProductivityActivityDTO> activities = result.getValue();
			}
    } catch (Exception e) { 
        TLoggerUtil.error(e.getMessage(), e);
            
    }
```

### List all employees using a org.tedros.fx.process.TProcess

The TProcess is a class that extends javafx.concurrent.Service and provides a way to call ejb controllers remotely in a background thread.

```java
    public class LoadEmployeeService extends TProcess<List<Employee>> {
    	
    	public LoadEmployeeService() {
    			
    	}
    	
    	@Override
		protected TTaskImpl<List<Employee>> createTask() {
			return new TTaskImpl<List<Employee>>() {
				@Override
				protected List<Employee> call() throws Exception {
					
					List<Employee> employees = new ArrayList<>();
					
					try (TEjbServiceLocator locator = TEjbServiceLocator.getInstance()) {
				        IEmployeeController controller = locator.lookup(IEmployeeController.JNDI_NAME);
				            
				        TResult<List<Employee>> result = controller.listAll(TedrosContext.getLoggedUser().getAccessToken(), Employee.class);
				    
				        if(result.isSuccess()) {
				        	employees = result.getValue();
					    }
				            
				    } 
					
					return employees; 
				}

				@Override
				public String getServiceNameInfo() {
					return null;
				}
			};
		}
    }
```

And to use it:

```java
    LoadEmployeeService employeeService = new LoadEmployeeService();
    employeeService.setOnSucceeded(e -> {
            	List<Employee> employees = employeeService.getValue();
			});
    employeeService.startProcess();
```