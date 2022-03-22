
const loginRoute = $("#loginRoute").val()
const validateRoute = $("#validateRoute").val()
const createRoute = $("#createRoute").val()
const csrfToken = $("#csrfToken").val()

$("#contents").load(loginRoute)

function login() {
  const username = $("#loginName").val()
  const password = $("#loginPass").val()
  $.post(
    validateRoute,
    { username, password, csrfToken },
    data => {
      $("#contents").html(data)
    }
  )
}
function createUser() {
  const username = $("#createName").val()
  const password = $("#createPass").val()
  $.post(
    createRoute,
    { username, password, csrfToken },
    data => {
      $("#contents").html(data)
    }
  )
}

function deleteTask(index) {
  // $("#contents").load("/deleteTask2?index=" + index)
  $.post(
    "/deleteTask2",
    { index, csrfToken },
    data => {
      $("#contents").html(data)
    }
  )
}

function addTask() {
  const task = $("#newTask").val()
  //$("#contents").load("addTask2?task=" + encodeURIComponent(taskName))
  $.post(
    "/addTask2",
    { task, csrfToken },
    data => {
      $("#contents").html(data)
    }
  )
}
