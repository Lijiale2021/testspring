function tick(language) {
    var isnMonth_cn = new Array('1��', '2��', '3��', '4��', '5��', '6��', '7��', '8��', '9��', '10��', '11��', '12��');
    var isnDay_cn = new Array('������', '����һ', '���ڶ�', '������', '������', '������', '������', '������');
    var isnMonth_en = new Array('JAN.', 'FEB.', 'MAR.', 'APR.', 'MAY.', 'JUN.', 'JUL.', 'AUG.', 'SEP.', 'OCT.', 'NOV.', 'DEC.');
    var isnDay_en = new Array('SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN');
    today = new Date();
    Year = today.getYear();
    Date1 = today.getDate();
    if (document.all) {
        if (language == "cn") {
            document.write(Year + '��' + isnMonth_cn[today.getMonth()] + Date1 + '�ա�' + isnDay_cn[today.getDay()])
        } else if (language == "en") {
            document.write(isnMonth_en[today.getMonth()] + Date1 + ',' + Year + '��' + isnDay_en[today.getDay()])
        }
    }
}