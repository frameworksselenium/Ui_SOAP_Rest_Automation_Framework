Feature: end request to 2 end points and compare 2 responses

  @test
  Scenario: 110:test
    Given softassert test "ram" and "ram"
    Given softassert test "ram" and "ram1"
    Given softassert test "ram" and "ram"
    Given hardassert test "ram" and "ram1"
    Given softassert test "krishna" and "krishna"

