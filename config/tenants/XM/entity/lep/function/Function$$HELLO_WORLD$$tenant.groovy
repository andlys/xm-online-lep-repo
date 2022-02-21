package function


functionInput = lepContext.inArgs.functionInput

// {{env}}api/functions/HELLO-WORLD?name=Ostap&surname=Vyshnia
if (!functionInput.containsKey("name"))
    throw new com.icthh.xm.commons.exceptions.BusinessException("error: parameter 'name' should be present")
else
    return ["success": functionInput]

//return ["test": "Hello world!!!", "input": functionInput, "supinput": functionInput.]