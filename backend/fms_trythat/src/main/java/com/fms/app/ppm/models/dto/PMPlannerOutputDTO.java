package com.fms.app.ppm.models.dto;

import java.util.List;

public class PMPlannerOutputDTO {
	 public static class Request {
	        private Integer count;
	        public Integer getCount() {
	            return count;
	        }
	        public void setCount(Integer count) {
	            this.count = count;
	        }
	    }
	 public static class Trade {
	        private String name;
	        private float count;
	        private String id;
	        private float availableCount;
	        
	        public Trade(String name, float count, String id, float availableCount) {
				super();
				this.name = name;
				this.count = count;
				this.id = id;
				this.availableCount = availableCount;
			}

			public String getName() {
	            return name;
	        }

	        public void setName(String name) {
	            this.name = name;
	        }

	        public float getCount() {
	            return count;
	        }

	        public void setCount(float count) {
	            this.count = count;
	        }

	        public String getId() {
	            return id;
	        }

	        public void setId(String id) {
	            this.id = id;
	        }

	        public float getAvailableCount() {
	            return availableCount;
	        }

	        public void setAvailableCount(float availableCount) {
	            this.availableCount = availableCount;
	        }
	    }
	 public static class Tool {
            private String name;
            private float count;
            private String id;
            private float availableCount;
            
            public Tool(String name, float count, String id, float availableCount) {
				super();
				this.name = name;
				this.count = count;
				this.id = id;
				this.availableCount = availableCount;
			}

			public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getCount() {
                return count;
            }

            public void setCount(float count) {
                this.count = count;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public float getAvailableCount() {
                return availableCount;
            }

            public void setAvailableCount(float availableCount) {
                this.availableCount = availableCount;
            }
	      }
	 public static class Part {
            private String name;
            private Integer count;
            private String id;
            private String units;
            private Integer availableCount;
            
            public Part(String name, Integer count, String id,String units,Integer availableCount) {
				super();
				this.name = name;
				this.count = count;
				this.id = id;
				this.setUnits(units);
				this.availableCount = availableCount;
			}

			public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Integer getCount() {
                return count;
            }

            public void setCount(Integer count) {
                this.count = count;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

			public String getUnits() {
				return units;
			}

			public void setUnits(String units) {
				this.units = units;
			}

			public Integer getAvailableCount() {
				return availableCount;
			}

			public void setAvailableCount(Integer availableCount) {
				this.availableCount = availableCount;
			}
			
	     }

	    public static class Technician {
            private String name;
            private float count;
            private String id;
            private float availableCount;
            
            public Technician(String name, float count, String id, float availableCount) {
				super();
				this.name = name;
				this.count = count;
				this.id = id;
				this.availableCount = availableCount;
			}

			public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public float getCount() {
                return count;
            }

            public void setCount(float count) {
                this.count = count;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public float getAvailableCount() {
                return availableCount;
            }

            public void setAvailableCount(float availableCount) {
                this.availableCount = availableCount;
            }
	    }

	    private Request Request;
	    private List<Trade> Trade;
	    private List<Tool> Tool;
	    private List<Part> Part;
	    private List<Technician> Technician;

	    public Request getRequest() {
	        return Request;
	    }

	    public void setRequest(Request request) {
	        this.Request = request;
	    }

		public List<Trade> getTrade() {
			return Trade;
		}

		public void setTrade(List<Trade> trade) {
			Trade = trade;
		}

		public List<Tool> getTool() {
			return Tool;
		}

		public void setTool(List<Tool> tool) {
			Tool = tool;
		}

		public List<Part> getPart() {
			return Part;
		}

		public void setPart(List<Part> part) {
			Part = part;
		}

		public List<Technician> getTechnician() {
			return Technician;
		}

		public void setTechnician(List<Technician> technician) {
			Technician = technician;
		}
	    
}
