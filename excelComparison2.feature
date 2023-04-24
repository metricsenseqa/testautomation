Feature: Excel Comparison of Existing Excel Sheets with Newly generated Excel from UI

Scenario: User is able to compare both the excel sheets
	Given user gets the  list of all the input files available in shared location
	When user will generate an excel from UI and compare with existing base Excel for each file in shared location
	
# Sub Step #	When user reads and gets the input data from input excel
# Sub Step #	Then user will Launch Browser and open webapplication
# Sub Step #	And enter data into given UI
# Sub Step #	And clicks on Generate Excel button to download the excel
# Sub Step #	When user checks if the excel file is downloaded successfully
# Sub Step #	And compares both the excel files

	Then user generates extent-report