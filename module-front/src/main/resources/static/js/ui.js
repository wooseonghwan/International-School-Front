var html = document.querySelector("html");
var body = document.querySelector("body");

let Sidebar = {
    init: function () {
        this.toggle();
    },
    toggle: function () {
        const menuBtn = document.querySelector(".menu-btn");
        const sideMenu = document.querySelector(".side-menu");
        const closeBtn = document.querySelector(".side-menu .close-btn");
        const dimmed = document.querySelector(".dimmed");

        // 메뉴 열기
        menuBtn?.addEventListener("click", () => {
            sideMenu.classList.add("active");
            dimmed.classList.add("active");
        });

        // 메뉴 닫기
        const closeMenu = () => {
            sideMenu.classList.remove("active");
            dimmed.classList.remove("active");
        };

        closeBtn?.addEventListener("click", closeMenu);
        dimmed?.addEventListener("click", closeMenu);
    },
};

let Navbar = {
    init: function () {
        this.toggle();
    },
    toggle: function () {
        const btnAllmenu = document.querySelector(".btn-allmenu");
        btnAllmenu?.addEventListener("click", function () {
            html.classList.add("sidebar-show");
        });

        const btnAllmenuClose = document.querySelector(".btn-allmenu-close");
        btnAllmenuClose?.addEventListener("click", function () {
            html.classList.remove("sidebar-show");
        });
    },
};

let Common = {
    init: function () {
        this.datepicker();
        this.buttonDatepicker();
        this.inputDelete();
        this.menuButton();
        this.passwordShow();
        this.fileUpload();
        this.toggleBtn();
    },
    datepicker: function () {
        $("[data-picker='date']").datepicker({
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: [
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
            ],
            monthNamesShort: [
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
            ],
            changeYear: true,
            changeMonth: true,
            showMonthAfterYear: true,
            dateFormat: "yy-mm-dd",
        });
    },
    buttonDatepicker: function () {
        $("[data-value-picker='date']").datepicker({
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: [
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
            ],
            monthNamesShort: [
                "01",
                "02",
                "03",
                "04",
                "05",
                "06",
                "07",
                "08",
                "09",
                "10",
                "11",
                "12",
            ],
            changeYear: true,
            changeMonth: true,
            showMonthAfterYear: true,
            dateFormat: "yy-mm-dd",
            onSelect: function () {
                $(this).val("시작일 변경");
            },
        });
    },
    inputDelete: function () {
        $(".input-form .form-control").on("input", function () {
            var $parent = $(this).closest(".form-field");
            var $button = $parent.find(".clear-button");
            if ($(this).val().length > 0) {
                $button.show();
            } else {
                $button.hide();
            }
        });

        $(".clear-button").on("click", function () {
            var $parent = $(this).closest(".form-field");
            var $input = $parent.find(".input-form .form-control");
            $input.val("");
            $(this).hide();
        });
    },
    menuButton: function () {
        const menuBtn = document.querySelector(".menu-btn");
        const sideMenu = document.querySelector(".side-menu");
        const closeBtn = document.querySelector(".side-menu .close-btn");

        menuBtn?.addEventListener("click", function () {
            sideMenu?.classList.add("active");
        });

        closeBtn?.addEventListener("click", function () {
            sideMenu?.classList.remove("active");
        });

        document.addEventListener("click", function (e) {
            if (!sideMenu?.contains(e.target) && !menuBtn?.contains(e.target)) {
                sideMenu?.classList.remove("active");
            }
        });
    },
    passwordShow: function () {
        const passwordInput = document.getElementById("password");
        const togglePassword = document.getElementById("togglePassword");

        togglePassword?.addEventListener("click", function () {
            const type =
                passwordInput.getAttribute("type") === "password"
                    ? "text"
                    : "password";
            passwordInput.setAttribute("type", type);

            const iconSrc =
                type === "password"
                    ? "../../../assets/images/icon-toggle-eyes.svg"
                    : "../../../assets/images/icon-toggle-eyes-active.svg";
            togglePassword.setAttribute("src", iconSrc);
        });
    },
    fileUpload: function () {
        const fileBtn = document.querySelector(".file-button");
        const fileInput = document.querySelector("#fileInput");
        const fileNameDisplay = document.querySelector(".file-name");

        fileBtn?.addEventListener("click", () => {
            fileInput?.click();
        });

        fileInput?.addEventListener("change", (event) => {
            const fileName = event.target.files[0]?.name || "선택된 파일 없음";
            fileNameDisplay.value = fileName;
        });
    },
    toggleBtn: function () {
        const toggleIds = ["langToggle", "langToggleMobile"];

        toggleIds.forEach((id) => {
            const toggle = document.getElementById(id);
            const ball = toggle?.querySelector(".toggle-ball");

            if (toggle && ball) {
                toggle.addEventListener("click", () => {
                    toggle.classList.toggle("active");
                    ball.textContent = toggle.classList.contains("active")
                        ? "En"
                        : "Ko";
                });
            }
        });
    },
};

Sidebar.init();
Navbar.init();
Common.init();