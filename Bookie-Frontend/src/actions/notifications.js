export const onNotificationSuccessInit = (notification, type) => ({
  type: type,
  payload: {notification, type: 'success'}
});
export const onNotificationErrorInit = (notification, type) => ({
  type: type,
  payload: {notification, type: 'error'}
});

export const onNotificationClose = () => ({ type: "NOTIFICATION_CLOSE" });